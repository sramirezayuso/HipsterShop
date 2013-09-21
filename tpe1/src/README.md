# Hipster Store

## First install

From the command line:

1. Install `grunt-cli` globally with `npm install -g grunt-cli`.
2. Install the [necessary local dependencies](package.json) via `npm install`

When completed, you'll be able to run the various Grunt commands provided from the command line.

**Unfamiliar with `npm`? Don't have node installed?** That's a-okay. npm stands for [node packaged modules](http://npmjs.org/) and is a way to manage development dependencies through node.js. [Download and install node.js](http://nodejs.org/download/) before proceeding.

## Compiling Bootstrap

### Only compile CSS and JavaScript - `grunt dist`
  `grunt dist` creates the `/dist` directory with compiled files. **Requires [recess](https://github.com/twitter/recess) and [uglify-js](https://github.com/mishoo/UglifyJS).**

#### Watch - `grunt watch`
  This is a convenience method for watching just Less files and automatically building them whenever you save.


