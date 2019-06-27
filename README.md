0 base-discovery 和 base-config
    基础服务,其他服务开启前,需要按顺序开启base-discovery和base-config
    开启后,可以登录网页localhost:8000查看
1 service-collector 
    从文件中采集数据,推送到Kafka
    接受socket数据，推送到Kafka
2 service-preprocessor 
    从kafka拉取原始数据,做相应的预处理
    将预处理的标准数据推送到Kafka,用于实时监测
    将预处理的标准数据存储到HBase,用于特征提取
    提供HBase数据导出到文件功能,用于离线建模
3 service-analyst
    提供已训练的模型(数据来源于文件)
4 service-zuul
    利用标准数据,做在线监测的可视化
    利用特征提取算法,做特征提取的可视化
    利用训练模型,做故障诊断的可视化

注意事项:
    在本机与集群切换时,记得修改hbase-site.xml等配置文件
    
    
    
    
    