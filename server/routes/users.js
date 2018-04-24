var express = require('express');
var router = express.Router();

/* GET users listing. */
router.get('/', function(req, res, next) {
  // res.send('respond with a resource');
  res.json({user:'all'});
});

/* GET users listing. */
router.get('/1', function(req, res, next) {
    // res.send('respond with a resource');
    res.json({user:1});
});

module.exports = router;
