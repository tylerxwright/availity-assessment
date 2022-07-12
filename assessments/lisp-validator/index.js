const lispValidator = require('./lisp-validator');
const readlineSync = require('readline-sync');

const lispString = readlineSync.question('Please enter a LISP string to validate: ');

const isValid = lispValidator(lispString);

var validResponse = !!isValid ? 'valid' : 'invalid';
console.log(`The string you entered is ${validResponse}`);
