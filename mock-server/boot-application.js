const express = require("express");
const app = express();
const generators = require('./product-repository');


const bashPath = process.env.BASE_PATH;

app.get(bashPath + "/products", function (req, res) {
    console.log("[GET] products")
    res.send(generators.getProducts())
});

app.get(bashPath + "/products/:productId", function (req, res) {
    console.log("[GET] products by ID")
    res.send(generators.findProductsByID(req.params.productId))
});

app.get(bashPath + "/product-search?:name", function (req, res) {
    let name = req.query.name;
    console.log("[GET] products by Name " + name);
    res.send(generators.findProductsByName(name));
});

app.listen(process.env.PORT, () => {
    console.log("El servidor este inicializado en el port: " + process.env.PORT)
});