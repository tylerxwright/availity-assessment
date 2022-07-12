const isLispStringValid = require('./lisp-validator');

describe("LISP Validator", () => {

  test('An empty string should be valid', () => {
    var isValid = isLispStringValid('');
    expect(isValid).toBe(true);
  });

  test('A valid LISP string should be valid', () => {
    var isValid = isLispStringValid('Test(())Test');
    expect(isValid).toBe(true);
  });

  test('A invalid LISP string should be invalid', () => {
    var isValid = isLispStringValid('Test())Test');
    expect(isValid).toBe(false);
  });

  test('It should support multiple types of parens', () => {
    var isValid = isLispStringValid('({[]})');
    expect(isValid).toBe(true);
  });

  test('It should ensure the correct parens are closed', () => {
    var isValid = isLispStringValid('({[}})');
    expect(isValid).toBe(false);
  });
});