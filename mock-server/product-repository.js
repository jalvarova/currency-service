const faker = require("@faker-js/faker").faker;
require("dotenv").config();

class Product {
    constructor(database) {
        this.database = {products: []};
    }
}


function generatorProducts() {
    let product = new Product()
    for (let i = 1; i <= process.env.COUNT_PRODUCTS; i++) {
        product.database.products.push({
            id: i,
            code: faker.random.numeric(6, {bannedDigits: ['0']}),
            name: faker.commerce.productName(),
            description: faker.lorem.sentences(),
            price: faker.commerce.price(),
            color: faker.commerce.color(),
            imageUrl: process.env.IMAGE_URL,
            quantity: faker.random.numeric(5)
        });
    }
    return product.database.products;
}

const productsDB = generatorProducts();

module.exports = {
    getProducts: function getProducts() {
        console.log(JSON.stringify(productsDB));
        return productsDB;
    },

    findProductsByName: function (name) {
        console.log('Search name ' + name);
        return productsDB
            .filter(x => x.name.includes(name))
    },
    findProductsByID: function (id) {
        console.log('Search id ' + id);
        return productsDB
            .filter(x => x.code == id);
    }
};
