package com.whut.dataQuery.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.whut.dataQuery.enumerate.DCSPoint;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.util.DigestUtils;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by 杨赟 on 2018-07-16.
 */
public class HBaseDaoUtil {


    public static String hashName(String name){
    return DigestUtils.md5DigestAsHex(name.getBytes()).substring(0, 6);
}

    public static String[] getSpiltKeys(int regionNum)
    {
        if (regionNum < 2)
            return null;
        Set<String> set = new TreeSet<>();
        DCSPoint[] points = DCSPoint.values();
        for (DCSPoint point : points)
        {
            set.add(hashName(point.getFullName()));
        }
        int size = set.size();
        if (size < points.length)
        {
            return null;
        }
        String[] array = new String[size];
        set.toArray(array);
        String[] keys = new String[regionNum-1];
        for (int i = 0; i < regionNum-1; ++i)
        {
            keys[i] = array[size/regionNum * (i+1)];
        }
        return keys;
    }

    //增加
    public static Put cellPut(String rowKey, String family, String qualifier, Object value)
    {
        return new Put(Bytes.toBytes(rowKey)).
                addColumn(family.getBytes(), qualifier.getBytes(), String.valueOf(value).getBytes());
    }
    public static Put rowPut(String rowKey, String family, Map<String,Object> qv)
    {
        Put put = new Put(Bytes.toBytes(rowKey));
        for(String q : qv.keySet()) {
            put.addColumn(family.getBytes(),q.getBytes(),String.valueOf(qv.get(q)).getBytes());
        }
        return put;
    }
    public static Put rowPut(String rowKey, Map<String,Map<String,Object>> fqv)
    {
        Put put = new Put(Bytes.toBytes(rowKey));
        for(String f : fqv.keySet()){
            Map<String,Object> qv = fqv.get(f);
            for(String q : qv.keySet()) {
                put.addColumn(f.getBytes(),q.getBytes(),String.valueOf(qv.get(q)).getBytes());
            }
        }
        return put;
    }
    //删除
    public static Delete cellDelete(String rowKey, String family, String qualifier)
    {
        return new Delete(Bytes.toBytes(rowKey)).
                addColumn(family.getBytes(), qualifier.getBytes());
    }
    public static Delete rowDelete(String rowKey)
    {
        return new Delete(Bytes.toBytes(rowKey));
    }
    //查
    public static Get cellGet(String rowKey, String family, String qualifier)
    {
        return new Get(Bytes.toBytes(rowKey)).
                addColumn(family.getBytes(),qualifier.getBytes());
    }
    public static Get rowGet(String rowKey)
    {
        return new Get(Bytes.toBytes(rowKey));
    }
    //扫描
    public static Scan setReversal(Scan scan) { return scan.setReversed(true); }
    public static Scan rowStart(Scan scan, String start)
    {
        return scan.withStartRow(start.getBytes());
    }
    public static Scan rowStop(Scan scan, String stop)
    {
        return scan.withStopRow(stop.getBytes());
    }
    public static Scan rowPrefix(Scan scan, String prefix)
    {
        return scan.setFilter(new RowFilter(CompareOperator.EQUAL,
                new BinaryPrefixComparator(prefix.getBytes())));
    }
    public static Scan rowSubstring(Scan scan, String substring)
    {
        return scan.setFilter(new RowFilter(CompareOperator.EQUAL,
                new SubstringComparator(substring)));
    }
    public static Scan rowRegex(Scan scan, String regex)
    {
        return scan.setFilter(new RowFilter(CompareOperator.EQUAL,
                new RegexStringComparator(regex)));
    }
    public static Scan addFamily(Scan scan, String family)
    {
        return scan.addFamily(family.getBytes());
    }
    public static Scan addColumn(Scan scan, String family, String qualifier)
    {
        return scan.addColumn(family.getBytes(),qualifier.getBytes());
    }
    public static Scan qualifierPrefix(Scan scan, String prefix)
    {
        return scan.setFilter(new ColumnPrefixFilter(prefix.getBytes()));
    }
    public static Scan qualifierRange(Scan scan, String mix, String max)
    {
        return scan.setFilter(new ColumnRangeFilter(mix.getBytes(),
                true,max.getBytes(),true));
    }
    public static Scan qualifierRegex(Scan scan, String regex)
    {
        return scan.setFilter(new QualifierFilter(CompareOperator.EQUAL,
                new RegexStringComparator(regex)));
    }
    public static Scan qualifierPagination(Scan scan, String start, int limit)
    {
        return scan.setFilter(new ColumnPaginationFilter(limit,start.getBytes()));
    }
    public static Scan qualifierPagination(Scan scan, int offset, int limit)
    {
        return scan.setFilter(new ColumnPaginationFilter(limit,offset));
    }
    public static Scan qualifierValueGreater(Scan scan, String family, String qualifier, String start)
    {
        return scan.setFilter(new ColumnValueFilter(
                family.getBytes(),
                qualifier.getBytes(),
                CompareOperator.GREATER_OR_EQUAL,
                start.getBytes()));
    }
    public static Scan qualifierValueLess(Scan scan, String family, String qualifier, String end)
    {
        return scan.setFilter(new ColumnValueFilter(
                family.getBytes(),
                qualifier.getBytes(),
                CompareOperator.LESS_OR_EQUAL,
                end.getBytes()));
    }
    public static Scan valueGreater(Scan scan, String start)
    {
        return scan.setFilter(new ValueFilter(
                CompareOperator.GREATER_OR_EQUAL,new BinaryComparator(start.getBytes())));
    }
    public static Scan valueLess(Scan scan, String end)
    {
        return scan.setFilter(new ValueFilter(
                CompareOperator.LESS_OR_EQUAL,new BinaryComparator(end.getBytes())));
    }

    private static JSONObject parseRow(Result row)
    {
        JSONObject jsonObject = new JSONObject();
        JSONObject object = new JSONObject();
        String timestamp = Bytes.toString(row.getRow()).substring(12,22);
        Cell[] cells = row.rawCells();
        if(cells !=null && cells.length>0)
        {
            for (Cell cell : cells) {
                String q = Bytes.toString(cell.getQualifierArray(),cell.getQualifierOffset(),cell.getQualifierLength());
                String v = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                object.put(q,v);
            }
            jsonObject.put("m",object);
            jsonObject.put("t",timestamp);
        }
        return jsonObject;
    }

    public static JSONArray parseScanner(ResultScanner scanner)
    {
        JSONArray array = new JSONArray();
        for (Result result : scanner) {
            array.add(parseRow(result));
        }
        return array;
    }

    public static JSONArray sampling(JSONArray src, int size)
    {
        JSONArray samplingArray = new JSONArray();
        int length = src.size();
        int internal = length/size;
        internal = internal>0 ? internal:1;
        for(int i=0;i<length;i+=internal)
        {
            samplingArray.add(src.getJSONObject(i));
        }
        return samplingArray;
    }
    public static JSONArray reBatch(JSONArray src, int size)
    {
        int start = src.size() - size;
        if(start <= 0)
        {
            return src;
        }
        JSONArray batchArray = new JSONArray();
        for(;start != src.size(); ++ start)
        {
            batchArray.add(src.get(start));
        }
        return batchArray;
    }
}
