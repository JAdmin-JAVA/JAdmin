$(function(){
	var canvas = $('#canvas')[0];
//	console.log(canvas);
	var ctx = canvas.getContext("2d");	//获取绘图环境
//	console.log(ctx);
	var num = 1; 
	function palette(canvas,ctx){	//初始化画布内部元素默认样式
		this.strokeColor = 'red';	//默认选中红色触发颜色
		this.fillColor = 'green';	//默认选中绿色填充色
		this.style = 'tablet';	//默认选中直线形状
		this.type = 'stroke';	//默认的绘制方式是划
		this.ctx = ctx;	//默认为绘图环境
		this.canvas = canvas;		//默认画布
		this.canvasW = canvas.width;	//默认画布宽
		this.canvasH = canvas.height;		//默认画布高
		this.history = [];	//默认的历史记录为数组
		this.edges = '3';	//默认的边数为3
	}
	var p = new palette(canvas,ctx);	//实例化出来一个palette,引进参数canvas和ctx
		
//	* 创建点击事件执行绘画方式
//	点击修改颜色
	$('.bg').on('click',function(){
		$(this).css('background','rgb(147, 255, 47)');
		$(this).siblings().css('background','#3652d7');
		$(this).find('a').css('color','#07133d');
		$(this).siblings().children('a').css('color','#ffffff');
	});
//	改变input.number数值,边数改变函数
	range.onchange=function(){
		p.edges=this.value;
	}
//	点击直线按钮执行直线绘画
	line.onclick = function(e){
//		console.log('我是直线');
		p.style = 'line';
//		console.log(line);
	}
//	点击矩形按钮执行矩形绘画
	rect.onclick = function(e){
//		console.log('我是矩形');
		p.style = 'rect';
	}
//	点击等腰三角形按钮执行等腰三角形绘画
	dytriangle.onclick = function(e){
//		console.log('我是等腰三角形');
		p.style = 'dytriangle';
	}
//	点击直角三角形按钮执行直角三角形绘画
	zjtriangle.onclick = function(e){
//		console.log('我是直角三角形');
		p.style = 'zjtriangle';
	}
//	点击多边形按钮执行多边三角形绘画
	polygon.onclick = function(e){
//		console.log('我是多边三角形');
		p.style = 'polygon';
	}	
//	点击绘画按钮执行绘画
	tablet.onclick = function(e){
		p.style = 'tablet';
	}
//	点击圆形绘画按钮执行圆形绘画
	circle.onclick = function(e){
		p.style = 'circle';
	}	
//	点击椭圆绘画按钮执行椭圆绘画
	ellipse.onclick = function(e){
		p.style = 'ellipse';
	}	
//	点击填充颜色按钮修改填充色
	fillCo.onchange = function(e){
    	p.fillColor=this.value;
    	p.strokeColor=this.value;
    }
//	点击橡皮按钮执行橡皮功能
	eraser.onclick = function(e){
		p.style = 'eraser';
		
	}
//	点击绘画方式按钮修绘画方式
	way.onclick = function(e){
		num*=-1;
		if(num==1){
			p.type="stroke";
//			console.log(this);
			$(this).find('span').css('color','#3653dd');
		}else{
			p.type="fill";
			$(this).find('span').css('color','#ffffff');
		}
	}	
//	点击刷新按钮执行页面刷新
	refresh.onclick=function(){
		p.history.length=0;
		p.ctx.clearRect(0, 0, p.canvasW, p.canvasH);
	}
//	点击修改线条宽度按钮修改线条宽度
	linew.oninput = function(e){
//		console.log(numW);
		ctx.lineWidth = this.value;
		numW.innerHTML = this.value;
	}
//	点击保存执行保存
	save.onclick=function(){
		p.save();
	}
//	点击撤销按钮返回上一层 that.history.length
	revo.onclick = function(){
	    p.revo();
	};
	
//	* 创建palette类下的对象
	//	绘画初始化
	palette.prototype.init = function(){
		this.ctx.strokeStyle = this.strokeColor;
//		strokeStyle 属性设置或返回用于笔触的颜色、渐变或模式。
		this.ctx.fillStyle = this.fillColor;
//		fillStyle 属性设置或返回用于填充绘画的颜色、渐变或模式。
	}
	//	绘制直线
	palette.prototype.line = function(x1,y1,x2,y2){
		this.ctx.beginPath();
//		beginPath() 方法开始一条路径，或重置当前的路径
		this.ctx.moveTo(x1 - 0.5,y1 - 0.5);
//		moveTo() 方法可把窗口的左上角移动到一个指定的坐标。
		this.ctx.lineTo(x2 - 0.5,y2 - 0.5);
//		lineTo() 方法添加一个新点，然后创建从该点到画布中最后指定点的线条（该方法并不会创建线条）。
		this.ctx.closePath();
//		closePath() 方法创建从当前点到开始点的路径。
		this.ctx.stroke();
//		stroke() 方法会实际地绘制出通过 moveTo() 和 lineTo() 方法定义的路径。默认颜色是黑色。
	}
	//绘制矩形
	palette.prototype.rect = function(x1,y1,x2,y2){
		this.ctx.beginPath();
	    this.ctx.rect(x1 - 0.5, y1 - 0.5, x2-x1, y2-y1);
	    this.ctx.closePath();
	    this.ctx[this.type]();
	}
//	绘制等腰三角形
	palette.prototype.dytriangle = function(x1,y1,x2,y2){
		this.ctx.beginPath();
        this.ctx.lineTo(x1, y1);
        this.ctx.lineTo(x2,y2);
        this.ctx.lineTo(2*x1-x2,y2);
        this.ctx.closePath();
        this.ctx[this.type]();
	}
//	绘制直角三角形
	palette.prototype.zjtriangle = function(x1,y1,x2,y2){
		this.ctx.beginPath();
		this.ctx.lineTo(x1,y1);
		this.ctx.lineTo(x2,y2);
		this.ctx.lineTo(x1,y2);
		this.ctx.closePath();
		this.ctx[this.type]();
	}
	//绘制多边形
	palette.prototype.polygon = function(x1,y1,x2,y2){	
		this.ctx.beginPath();
//		console.log(this.edges)
        var deg=360/this.edges;
//      console.log(deg);
        var r= Math.sqrt(Math.pow(x2-x1,2),Math.pow(y2-y1),2);
//      console.log(r);
        for(var i=0;i<this.edges;i++){
	        var x=r*Math.sin(deg*i*Math.PI/180);
	        var y=r*Math.cos(deg*i*Math.PI/180)*(-1);
	        this.ctx.lineTo(x1+x,y1+y);
	    }
		this.ctx.closePath();
		this.ctx[this.type]();
	}
//	绘制圆形
	palette.prototype.circle = function(x1,y1,x2,y2){
		this.ctx.beginPath();
		var r= Math.sqrt(Math.pow(x2-x1,2),Math.pow(y2-y1),2);
		this.ctx.arc(x1,y1,r,0,2*Math.PI);
		this.ctx.closePath();
		this.ctx[this.type]();
	}
//	绘制写字板
	palette.prototype.tablet = function(x1,y1,x2,y2){
		this.ctx.lineTo(x2, y2);
		this.ctx[this.type]();
	}
//	绘制椭圆
	palette.prototype.ellipse = function(x1,y1,x2,y2){
		this.ctx.beginPath();
		for(var i=0;i<2*Math.PI;i+=0.01){
			ctx.lineTo(((x2-x1)/2)*Math.cos(i)+(x2+x1)/2,((y2-y1)/2)*Math.sin(i)+(y2+y1)/2);
		}
		this.ctx.closePath();
		this.ctx[this.type]();
	}
//	保存
	palette.prototype.save = function(x1,y1,x2,y2){
		location.href = canvas.toDataURL().replace('image/png','image/stream');
	}
//	橡皮
	palette.prototype.eraser = function(x1,y1,x2,y2){
        this.ctx.clearRect(x2, y2, 10, 10);
    }
//	撤回
	palette.prototype.revo = function(x1,y1,x2,y2){
        if ( this.history.length == 0) {
	        alert("无效操作");
	        return;
	    }
        console.log(this.history.length);
	    ctx.clearRect(0, 0, this.canvasW, this.canvasH);
	    this.history.pop();
	    if (this.history.length == 0) {
	    	return;
	    }
	    this.ctx.putImageData(this.history[this.history.length - 1], 0, 0);
    }
//	书写绘画函数
	palette.prototype.drawing = function(){
		var that = this;
//		console.log(this);	
		this.canvas.onmousedown = function(e){	//鼠标移动画布的函数
//			* 获取鼠标起始位置
			var sx = e.offsetX;
//			获取鼠标到时间源的宽度
//			console.log(sx);
			var sy = e.offsetY;		
//			获取鼠标到时间源的高度
//			console.log(sy);
			that.init();	//初始化样式
			if(that.style=="tablet"){
		    	that.ctx.beginPath();
//	    	    beginPath() 方法开始一条路径，或重置当前的路径
	    		that.ctx.moveTo(sx,sy);
//	    	    moveTo() 方法可把窗口的左上角移动到一个指定的坐标。
	    	}
//			获取鼠标移动时的坐标
			this.onmousemove = function(e){
				var mx = e.offsetX;
//				console.log(mx);
				var my = e.offsetY;
//				console.log(my);
				if (that.style!= "eraser") {
                    that.ctx.clearRect(0, 0, that.canvasW, that.canvasH);
//                  console.log(that.canvasW + ',' + that.canvasH);
//					清除鼠标在画布移动的填充色				
	   				if(that.history.length>0){	//注：只能是that.history数组的长度大于0，才可以putImageData()
		    			that.ctx.putImageData(that.history[that.history.length-1],0,0);
//	    				putImageData() 方法将图像数据（从指定的 ImageData 对象）放回画布上。
		    		}
            }
//				console.log(that.history.length);
				that[that.style](sx,sy,mx,my);
			}	
//			获取鼠标移走的坐标
			this.onmouseup = function(){
				that.history.push(that.ctx.getImageData(0,0,that.canvasW,that.canvasH));
//				getImageData() 方法返回 ImageData 对象，该对象拷贝了画布指定矩形的像素数据。
				this.onmousemove=null;
	    		// 清空鼠标移动事件
	    		this.onmouseup=null;
			    // 清空鼠标移出事件
			}
		}
	}
	p.drawing();	//调用drawing函数
});
