// FIXME: 按组区分 import 语句
// 1. NodeJS 标准库
// 2. 第三方库
// 3. 自己的包
// 每一组间用一个空行分割
// 每一组内按字典序排序

// TODO 增加 README.md
// TODO 注释、文档暂时先用中文写

const express = require('express');
const MongoClient = require('mongodb').MongoClient;
const createError = require('http-errors');
const cookieParser = require('cookie-parser');
const logger = require('morgan');

const cardsRouter = require('./routes/cards');


// FIXME: 相关配置参数设置为可配置（配置文件或/和命令行参数）
const url = 'mongodb://hearthintellect-mongo:27017/';

MongoClient.connect(url, function (err, db) {
    if (err)
        throw err;
    console.log('数据库已创建!');
    global.db = db.db('hearthstone');
    global.db.collection('cards').ensureIndex({name: 'text', text: 'text'});
});

const app = express();

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({extended: false}));
app.use(cookieParser());

app.use('/cards', cardsRouter);

// catch 404 and forward to error handler
app.use(function (req, res, next) {
    next(createError(404));
});

// error handler
app.use(function (err, req, res, next) {
    // set locals, only providing error in development
    res.locals.message = err.message;
    res.locals.error = req.app.get('env') === 'development' ? err : {};
    // render the error page
    res.status(err.status || 500);
});

module.exports = app;
