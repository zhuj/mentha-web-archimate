const webpack = require('webpack');
const merge = require('webpack-merge');
const config = require('./webpack.config.base');

const env = {
  NODE_ENV: JSON.stringify('development')
};

const GLOBALS = {
  'process.env': env,
  __DEV__: JSON.stringify(JSON.parse(process.env.DEBUG || 'true'))
};

module.exports = merge(config, {
  cache: true,
  devtool: 'source-map',
  entry: {
    app: [
      'webpack-hot-middleware/client?path=http://localhost:8080/__webpack_hmr',
      'react-hot-loader/patch',
      'index'
    ]
  },
  plugins: [
    new webpack.HotModuleReplacementPlugin(),
    new webpack.NoEmitOnErrorsPlugin(),
    new webpack.DefinePlugin(GLOBALS)
  ]
});
