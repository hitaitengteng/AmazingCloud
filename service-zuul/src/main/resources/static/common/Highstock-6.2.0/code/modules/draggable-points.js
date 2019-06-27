/*
 Highcharts JS v6.2.0 (2018-10-17)

 (c) 2009-2018 Torstein Honsi

 License: www.highcharts.com/license
*/
(function(n){"object"===typeof module&&module.exports?module.exports=n:"function"===typeof define&&define.amd?define(function(){return n}):n(Highcharts)})(function(n){(function(h){function n(a){var b=["draggableX","draggableY"],c;m(a.dragDropProps,function(a){a.optionName&&b.push(a.optionName)});for(c=b.length;c--;)if(a.options.dragDrop[b[c]])return!0}function N(a){var b=a.series.length;if(a.hasCartesianSeries&&!a.polar)for(;b--;)if(a.series[b].options.dragDrop&&n(a.series[b]))return!0}function O(a){var b=
a.series,c=b.options.dragDrop;a=a.options&&a.options.dragDrop;var e,d;m(b.dragDropProps,function(a){"x"===a.axis&&a.move?e=!0:"y"===a.axis&&a.move&&(d=!0)});return(c.draggableX&&e||c.draggableY&&d)&&!(a&&!1===a.draggableX&&!1===a.draggableY)&&b.yAxis&&b.xAxis}function P(a,b,c){var e=b.dragDropData.origin;b=e.pageX;var e=e.pageY,d=a.pageX;a=a.pageY;return Math.sqrt((d-b)*(d-b)+(a-e)*(a-e))>c}function Q(a,b,c){var e={pageX:a.pageX,pageY:a.pageY,guideBox:c&&{x:c.attr("x"),y:c.attr("y"),width:c.attr("width"),
height:c.attr("height")},points:{}};y(b,function(a){var b={};m(a.series.dragDropProps,function(c,e){b[e]=a[e]});b.point=a;e.points[a.id]=b});return e}function R(a){var b=a.series,c=b.options.dragDrop.groupBy;return a.options[c]?S(b.points,function(b){return b.options[c]===a.options[c]}):[a]}function C(a,b){var c=R(b),e=b.series,d=e.chart,l;r(e.options.dragDrop&&e.options.dragDrop.liveRedraw,!0)||(d.dragGuideBox=l=e.getGuideBox(c),d.setGuideBoxState("default",e.options.dragDrop.guideBox).add(e.group));
d.dragDropData={origin:Q(a,c,l),point:b,groupedPoints:c};d.isDragging=!0}function T(a,b){var c=a.point,e=q(c.series.options.dragDrop,c.options.dragDrop),d={},l=a.updateProp,B={};m(c.series.dragDropProps,function(a,b){if(!l||l===b&&a.resize&&(!a.optionName||!1!==e[a.optionName]))if(l||a.move&&("x"===a.axis&&e.draggableX||"y"===a.axis&&e.draggableY))d[b]=a});y(l?[c]:a.groupedPoints,function(c){B[c.id]={point:c,newValues:c.getDropValues(a.origin,b,d)}});return B}function D(a,b){var c=a.dragDropData.newPoints;
b=!1===b?!1:q({duration:400},a.options.animation);a.isDragDropAnimating=!0;m(c,function(a){a.point.update(a.newValues,!1)});a.redraw(b);setTimeout(function(){delete a.isDragDropAnimating;a.hoverPoint&&!a.dragHandles&&a.hoverPoint.showDragHandles()},b.duration)}function E(a){var b=a.series&&a.series.chart;b&&b.dragHandles&&(!b.dragDropData||!b.dragDropData.isDragging&&b.dragDropData.isHoveringHandle!==a.id)&&b.hideDragHandles()}function F(a){var b=0,c;for(c in a)a.hasOwnProperty(c)&&b++;return b}function G(a){for(var b in a)if(a.hasOwnProperty(b))return a[b]}
function H(a,b){if(!b.zoomOrPanKeyPressed(a)){var c=b.dragDropData,e,d;d=0;c&&c.isDragging&&(e=c.point,d=e.series.options.dragDrop,a.preventDefault(),c.draggedPastSensitivity||(c.draggedPastSensitivity=P(a,b,r(e.options.dragDrop&&e.options.dragDrop.dragSensitivity,d&&d.dragSensitivity,2))),c.draggedPastSensitivity&&(c.newPoints=T(c,a),b=c.newPoints,d=F(b),b=1===d?G(b):null,e.firePointEvent("drag",{origin:c.origin,newPoints:c.newPoints,newPoint:b&&b.newValues,newPointId:b&&b.point.id,numNewPoints:d,
pageX:a.pageX,pageY:a.pageY},function(){var b=e.series,c=b.chart,d=c.dragDropData,f=q(b.options.dragDrop,e.options.dragDrop),g=f.draggableX,h=f.draggableY,b=d.origin,k=a.pageX-b.pageX,v=a.pageY-b.pageY,t=k,d=d.updateProp;c.inverted&&(k=-v,v=-t);if(r(f.liveRedraw,!0))D(c,!1),c.dragHandles&&c.hideDragHandles(),e.showDragHandles();else if(d){var t=k,g=v,c=e.series,p=c.chart,h=p.dragDropData,m,f=c.dragDropProps[h.updateProp];m=h.newPoints[e.id].newValues;d="function"===typeof f.resizeSide?f.resizeSide(m,
e):f.resizeSide;f.beforeResize&&f.beforeResize(p.dragGuideBox,m,e);var p=p.dragGuideBox,t="x"===f.axis?t-(h.origin.prevdX||0):0,g="y"===f.axis?g-(h.origin.prevdY||0):0,u;switch("x"===f.axis&&c.xAxis.reversed||"y"===f.axis&&c.yAxis.reversed?{left:"right",right:"left",top:"bottom",bottom:"top"}[d]:d){case "left":u={x:p.attr("x")+t,width:Math.max(1,p.attr("width")-t)};break;case "right":u={width:Math.max(1,p.attr("width")+t)};break;case "top":u={y:p.attr("y")+g,height:Math.max(1,p.attr("height")-g)};
break;case "bottom":u={height:Math.max(1,p.attr("height")+g)}}p.attr(u)}else c.dragGuideBox.translate(g?k:0,h?v:0);b.prevdX=k;b.prevdY=v})))}}function z(a,b){var c=b.dragDropData;if(c&&c.isDragging&&c.draggedPastSensitivity){var e=c.point,d=c.newPoints,l=F(d),f=1===l?G(d):null;b.dragHandles&&b.hideDragHandles();a.preventDefault();b.cancelClick=!0;e.firePointEvent("drop",{origin:c.origin,pageX:a.pageX,pageY:a.pageY,newPoints:d,numNewPoints:l,newPoint:f&&f.newValues,newPointId:f&&f.point.id},function(){D(b)})}delete b.dragDropData;
b.dragGuideBox&&(b.dragGuideBox.destroy(),delete b.dragGuideBox)}function J(a,b){var c=b.hoverPoint;b.cancelClick=!1;b.zoomOrPanKeyPressed(a)||(b.mouseIsDown=!1,b.dragDropData&&b.dragDropData.isDragging?z(a,b):c&&O(c)&&(C(a,c),b.dragDropData.isDragging=!0,c.firePointEvent("dragStart",a)))}var k=h.addEvent,y=h.each,m=h.objectEach,r=h.pick,S=h.grep,q=h.merge,f=h.seriesTypes,K=function(a){a=a.shapeArgs||a.graphic.getBBox();var b=a.r||0,c=a.height/2;return["M",0,b,"L",0,c-5,"A",1,1,0,0,0,0,c+5,"A",1,
1,0,0,0,0,c-5,"M",0,c+5,"L",0,a.height-b]},w=f.line.prototype.dragDropProps={x:{axis:"x",move:!0},y:{axis:"y",move:!0}};f.flags&&(f.flags.prototype.dragDropProps=w);var g=f.column.prototype.dragDropProps={x:{axis:"x",move:!0},y:{axis:"y",move:!1,resize:!0,beforeResize:function(a,b,c){var e=c.series.translatedThreshold,d=a.attr("y");b.y>=c.series.options.threshold?(b=a.attr("height"),a.attr({height:Math.max(0,Math.round(b+(e?e-(d+b):0)))})):a.attr({y:Math.round(d+(e?e-d:0))})},resizeSide:function(a,
b){return a.y>=(b.series.options.threshold||0)?"top":"bottom"},handlePositioner:function(a){var b=a.shapeArgs||a.graphic.getBBox();return{x:b.x,y:a.y>=(a.series.options.threshold||0)?b.y:b.y+b.height}},handleFormatter:function(a){a=a.shapeArgs;var b=a.r||0,c=a.width/2;return["M",b,0,"L",c-5,0,"A",1,1,0,0,0,c+5,0,"A",1,1,0,0,0,c-5,0,"M",c+5,0,"L",a.width-b,0]}}};f.bullet&&(f.bullet.prototype.dragDropProps={x:g.x,y:g.y,target:{optionName:"draggableTarget",axis:"y",move:!0,resize:!0,resizeSide:"top",
handlePositioner:function(a){var b=a.targetGraphic.getBBox();return{x:a.barX,y:b.y+b.height/2}},handleFormatter:g.y.handleFormatter}});f.columnrange&&(f.columnrange.prototype.dragDropProps={x:{axis:"x",move:!0},low:{optionName:"draggableLow",axis:"y",move:!0,resize:!0,resizeSide:"bottom",handlePositioner:function(a){a=a.shapeArgs||a.graphic.getBBox();return{x:a.x,y:a.y+a.height}},handleFormatter:g.y.handleFormatter,propValidate:function(a,b){return a<=b.high}},high:{optionName:"draggableHigh",axis:"y",
move:!0,resize:!0,resizeSide:"top",handlePositioner:function(a){a=a.shapeArgs||a.graphic.getBBox();return{x:a.x,y:a.y}},handleFormatter:g.y.handleFormatter,propValidate:function(a,b){return a>=b.low}}});f.boxplot&&(f.boxplot.prototype.dragDropProps={x:g.x,low:{optionName:"draggableLow",axis:"y",move:!0,resize:!0,resizeSide:"bottom",handlePositioner:function(a){return{x:a.shapeArgs.x,y:a.lowPlot}},handleFormatter:g.y.handleFormatter,propValidate:function(a,b){return a<=b.q1}},q1:{optionName:"draggableQ1",
axis:"y",move:!0,resize:!0,resizeSide:"bottom",handlePositioner:function(a){return{x:a.shapeArgs.x,y:a.q1Plot}},handleFormatter:g.y.handleFormatter,propValidate:function(a,b){return a<=b.median&&a>=b.low}},median:{axis:"y",move:!0},q3:{optionName:"draggableQ3",axis:"y",move:!0,resize:!0,resizeSide:"top",handlePositioner:function(a){return{x:a.shapeArgs.x,y:a.q3Plot}},handleFormatter:g.y.handleFormatter,propValidate:function(a,b){return a<=b.high&&a>=b.median}},high:{optionName:"draggableHigh",axis:"y",
move:!0,resize:!0,resizeSide:"top",handlePositioner:function(a){return{x:a.shapeArgs.x,y:a.highPlot}},handleFormatter:g.y.handleFormatter,propValidate:function(a,b){return a>=b.q3}}});f.ohlc&&(f.ohlc.prototype.dragDropProps={x:g.x,low:{optionName:"draggableLow",axis:"y",move:!0,resize:!0,resizeSide:"bottom",handlePositioner:function(a){return{x:a.shapeArgs.x,y:a.plotLow}},handleFormatter:g.y.handleFormatter,propValidate:function(a,b){return a<=b.open&&a<=b.close}},high:{optionName:"draggableHigh",
axis:"y",move:!0,resize:!0,resizeSide:"top",handlePositioner:function(a){return{x:a.shapeArgs.x,y:a.plotHigh}},handleFormatter:g.y.handleFormatter,propValidate:function(a,b){return a>=b.open&&a>=b.close}},open:{optionName:"draggableOpen",axis:"y",move:!0,resize:!0,resizeSide:function(a){return a.open>=a.close?"top":"bottom"},handlePositioner:function(a){return{x:a.shapeArgs.x,y:a.plotOpen}},handleFormatter:g.y.handleFormatter,propValidate:function(a,b){return a<=b.high&&a>=b.low}},close:{optionName:"draggableClose",
axis:"y",move:!0,resize:!0,resizeSide:function(a){return a.open>=a.close?"bottom":"top"},handlePositioner:function(a){return{x:a.shapeArgs.x,y:a.plotClose}},handleFormatter:g.y.handleFormatter,propValidate:function(a,b){return a<=b.high&&a>=b.low}}});if(f.arearange){var w=f.columnrange.prototype.dragDropProps,L=function(a){a=a.graphic?a.graphic.getBBox().width/2+1:4;return["M",0-a,0,"a",a,a,0,1,0,2*a,0,"a",a,a,0,1,0,-2*a,0]};f.arearange.prototype.dragDropProps={x:w.x,low:{optionName:"draggableLow",
axis:"y",move:!0,resize:!0,resizeSide:"bottom",handlePositioner:function(a){return(a=a.lowerGraphic&&a.lowerGraphic.getBBox())?{x:a.x+a.width/2,y:a.y+a.height/2}:{x:-999,y:-999}},handleFormatter:L,propValidate:w.low.propValidate},high:{optionName:"draggableHigh",axis:"y",move:!0,resize:!0,resizeSide:"top",handlePositioner:function(a){return(a=a.upperGraphic&&a.upperGraphic.getBBox())?{x:a.x+a.width/2,y:a.y+a.height/2}:{x:-999,y:-999}},handleFormatter:L,propValidate:w.high.propValidate}}}f.waterfall&&
(f.waterfall.prototype.dragDropProps={x:g.x,y:q(g.y,{handleFormatter:function(a){return a.isSum||a.isIntermediateSum?null:g.y.handleFormatter(a)}})});if(f.xrange)var M=function(a,b){var c=a.series,e=c.xAxis,d=c.yAxis,c=c.chart.inverted;b=e.toPixels(a[b],!0);var f=d.toPixels(a.y,!0);c?(b=e.len-b,f=d.len-f-a.shapeArgs.height/2):f-=a.shapeArgs.height/2;return{x:Math.round(b),y:Math.round(f)}},A=f.xrange.prototype.dragDropProps={y:{axis:"y",move:!0},x:{optionName:"draggableX1",axis:"x",move:!0,resize:!0,
resizeSide:"left",handlePositioner:function(a){return M(a,"x")},handleFormatter:K,propValidate:function(a,b){return a<=b.x2}},x2:{optionName:"draggableX2",axis:"x",move:!0,resize:!0,resizeSide:"right",handlePositioner:function(a){return M(a,"x2")},handleFormatter:K,propValidate:function(a,b){return a>=b.x}}};f.gantt&&(f.gantt.prototype.dragDropProps={y:A.y,start:q(A.x,{optionName:"draggableStart",validateIndividualDrag:function(a){return!a.milestone}}),end:q(A.x2,{optionName:"draggableEnd",validateIndividualDrag:function(a){return!a.milestone}})});
y("gauge pie sunburst wordcloud sankey histogram pareto vector windbarb treemap bellcurve sma map mapline".split(" "),function(a){f[a]&&(f[a].prototype.dragDropProps=null)});var U={default:{className:"highcharts-drag-box-default",lineWidth:1,lineColor:"#888",color:"rgba(0, 0, 0, 0.1)",cursor:"move",zIndex:900}},V={className:"highcharts-drag-handle",color:"#fff",lineColor:"rgba(0, 0, 0, 0.6)",lineWidth:1,zIndex:901};h.Chart.prototype.setGuideBoxState=function(a,b){var c=this.dragGuideBox;b=q(U,b);
a=q(b.default,b[a]);return c.attr({className:a.className,stroke:a.lineColor,strokeWidth:a.lineWidth,fill:a.color,cursor:a.cursor,zIndex:a.zIndex})};h.Point.prototype.getDropValues=function(a,b,c){var e=this,d=e.series,f=q(d.options.dragDrop,e.options.dragDrop),g=d.yAxis,x=d.xAxis,h=b.pageX-a.pageX;b=b.pageY-a.pageY;var k=r(a.x,e.x),I=r(a.y,e.y),n=x.toValue(x.toPixels(k,!0)+(x.horiz?h:b),!0)-k,v=g.toValue(g.toPixels(I,!0)+(g.horiz?h:b),!0)-I,t={},p,w=a.points[e.id],u;for(u in c)if(c.hasOwnProperty(u)){if(void 0!==
p){p=!1;break}p=!0}m(c,function(a,b){var c=w[b],l;l=c+("x"===a.axis?n:v);var g=a.axis.toUpperCase(),h=d[g.toLowerCase()+"Axis"].categories?1:0,h=r(f["dragPrecision"+g],h),x=r(f["dragMin"+g],-Infinity),g=r(f["dragMax"+g],Infinity);h&&(l=Math.round(l/h)*h);l=Math.max(x,Math.min(g,l));p&&a.propValidate&&!a.propValidate(l,e)||void 0===c||(t[b]=l)});return t};h.Series.prototype.getGuideBox=function(a){var b=this.chart,c=Infinity,e=-Infinity,d=Infinity,f=-Infinity,g;y(a,function(a){(a=a.graphic&&a.graphic.getBBox()||
a.shapeArgs)&&(a.width||a.height||a.x||a.y)&&(g=!0,c=Math.min(a.x,c),e=Math.max(a.x+a.width,e),d=Math.min(a.y,d),f=Math.max(a.y+a.height,f))});return g?b.renderer.rect(c,d,e-c,f-d):b.renderer.g()};h.Point.prototype.showDragHandles=function(){var a=this,b=a.series,c=b.chart,e=c.renderer,f=q(b.options.dragDrop,a.options.dragDrop);m(b.dragDropProps,function(d,g){var h=q(V,d.handleOptions,f.dragHandle),l={className:h.className,"stroke-width":h.lineWidth,fill:h.color,stroke:h.lineColor},m=h.pathFormatter||
d.handleFormatter,n=d.handlePositioner,r=d.validateIndividualDrag?d.validateIndividualDrag(a):!0;d.resize&&r&&d.resizeSide&&m&&(f["draggable"+d.axis.toUpperCase()]||f[d.optionName])&&!1!==f[d.optionName]&&(c.dragHandles||(c.dragHandles={group:e.g("drag-drop-handles").add(b.markerGroup||b.group)}),n=n(a),m=m(a),!m||0>n.x||0>n.y||(l.cursor=h.cursor||"x"===d.axis!==!!c.inverted?"ew-resize":"ns-resize",c.dragHandles["function"===typeof d.resizeSide?d.resizeSide(a.options,a):d.resizeSide]=d=e.path(m).translate(n.x,
n.y).attr(l).add(c.dragHandles.group),k(d.element,"mousedown",function(b){var c=a.series.chart;c.zoomOrPanKeyPressed(b)||(c.mouseIsDown=!1,C(b,a),c.dragDropData.isDragging=!0,c.dragDropData.updateProp=b.updateProp=g,a.firePointEvent("dragStart",b),b.stopPropagation(),b.preventDefault())}),k(c.dragHandles.group.element,"mouseover",function(){c.dragDropData=c.dragDropData||{};c.dragDropData.isHoveringHandle=a.id}),k(c.dragHandles.group.element,"mouseout",function(){var b=a.series.chart;b.dragDropData&&
a.id===b.dragDropData.isHoveringHandle&&delete b.dragDropData.isHoveringHandle;b.hoverPoint||E(a)})))})};h.Chart.prototype.hideDragHandles=function(){this.dragHandles&&(m(this.dragHandles,function(a,b){"group"!==b&&a.destroy&&a.destroy()}),this.dragHandles.group&&this.dragHandles.group.destroy&&this.dragHandles.group.destroy(),delete this.dragHandles)};k(h.Point,"mouseOver",function(){var a=this;setTimeout(function(){var b=a.series,c=b&&b.chart;!c||c.dragDropData&&c.dragDropData.isDragging||c.isDragDropAnimating||
!b.options.dragDrop||c.options&&c.options.chart&&c.options.chart.options3d||(c.dragHandles&&c.hideDragHandles(),a.showDragHandles())},12)});k(h.Point,"mouseOut",function(){var a=this;setTimeout(function(){E(a)},10)});h.Chart.prototype.zoomOrPanKeyPressed=function(a){var b=this.userOptions.chart||{},c=b.panKey&&b.panKey+"Key";return a[b.zoomKey&&b.zoomKey+"Key"]||a[c]};h.Chart.prototype.callbacks.push(function(a){var b=a.container,c=h.doc;N(a)&&(k(b,"mousemove",function(b){H(b,a)}),k(b,"touchmove",
function(b){H(b,a)}),k(b,"mousedown",function(b){J(b,a)}),k(b,"touchstart",function(b){J(b,a)}),k(b,"mouseleave",function(b){z(b,a)}),a.unbindDragDropMouseUp=k(c,"mouseup",function(b){z(b,a)}),a.unbindDragDropTouchEnd=k(c,"touchend",function(b){z(b,a)}),k(a,"destroy",function(){a.unbindDragDropMouseUp&&a.unbindDragDropMouseUp();a.unbindDragDropTouchEnd&&a.unbindDragDropTouchEnd()}))})})(n)});
//# sourceMappingURL=draggable-points.js.map