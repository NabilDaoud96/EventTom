const path = require('path');

module.exports = {
  runtimeCompiler: true,
  transpileDependencies: [],
  configureWebpack: {
    resolve: {
      alias: {
        '@': path.resolve(__dirname, 'src/')
      }
    }
  }
};
