
module.exports = {
	lintOnSave:false,
	publicPath: './',
	devServer: {
		open: true,
		host: 'localhost',
		port: 8000,
		https: false,
		//以上的ip和端口是我们本机的;下面为需要跨域的   生产时需要关闭代理
		proxy: {//配置跨域
			'/api': {
				target: 'http://localhost:12000/api/',//这里后台的地址模拟的;应该填写你们真实的后台接口
				ws: true,
				changOrigin: true,//允许跨域
				pathRewrite: {
					'^/api': ''//请求的时候使用这个api就可以
				}
			}

		}
	}
};
