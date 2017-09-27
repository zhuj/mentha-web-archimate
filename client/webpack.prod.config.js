const webpack = require("webpack");
const ExtractTextPlugin = require('extract-text-webpack-plugin');

module.exports = require('./webpack.config.js');    // inherit from the main config file

// clear
module.exports.devServer = undefined;
module.exports.devtool = undefined;
module.exports.plugins = [];

// production env
module.exports.plugins.push(
  new webpack.DefinePlugin({
    'process.env': {
      NODE_ENV: JSON.stringify('production'),
    }
  })
);

// extract css text
module.exports.plugins.push(
  new ExtractTextPlugin('../css/main.css')
);

// // compress the js file
module.exports.plugins.push(
  new webpack.optimize.UglifyJsPlugin({
    comments: false,
    compressor: {
      warnings: false
    }
  })
);
