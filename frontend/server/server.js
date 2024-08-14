const express = require('express');
const { createProxyMiddleware } = require('http-proxy-middleware');

const app = express();

// Set up proxy for your backend API
app.use('/api', createProxyMiddleware({
  target: 'http://localhost:8080/notex_backend', // Replace with your backend's URL
  changeOrigin: true,
  secure: false, // For non-HTTPS backend, set to true for HTTPS
  cookieDomainRewrite: 'localhost', // Set to your frontend's domain in production
}));

// Serve static files from the CRA build
app.use(express.static('../build'));

// Start the server
app.listen(3000, () => {
  console.log('Custom proxy server listening on port 3000');
});