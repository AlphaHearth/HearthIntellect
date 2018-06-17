// FIXME: 按组区分 import 语句
// 1. NodeJS 标准库
// 2. 第三方库
// 3. 自己的包
// 每一组间用一个空行分割
// 每一组内按字典序排序

// TODO 增加 README.md
// TODO 注释、文档暂时先用中文写

const express = require('express');
const createError = require('http-errors');
const cookieParser = require('cookie-parser');

const cardsRouter = require('./routes/cards');
const logger = require('./logging').logger;

const app = express();

// info logger
app.use(function (req, res, next) {
    logger.info(req.method, req.originalUrl);
    next();
});

app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());

app.use('/cards', cardsRouter);

// catch 404 and forward to error handler
app.use(function (req, res, next) {
    next(createError(404));
});

// error handler
app.use(function (err, req, res, next) {
    logger.error(err);
    res.status(500).send({ error: err.toString() });
});

module.exports = app;
