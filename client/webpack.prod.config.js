var webpack = require("webpack");
module.exports = require('./webpack.config.js');    // inherit from the main config file

// production env
module.exports.plugins.push(
  new webpack.DefinePlugin({
    'process.env': {
      NODE_ENV: JSON.stringify('production'),
    }
  })
);

// compress the js file
module.exports.plugins.push(
  new webpack.optimize.UglifyJsPlugin({
    comments: false,
    compressor: {
      warnings: false
    }
  })
);
