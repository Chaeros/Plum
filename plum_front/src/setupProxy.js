const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app){
  app.use(
      createProxyMiddleware('/user', {
          target: 'https://loaclhost:8080',
          changeOrigin: true
      })
  )
};