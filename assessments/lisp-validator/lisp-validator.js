function isValid(lispString) {
  if(lispString.length === 0) {
    return true;
  }

  const parenStack = [];
  const paranCharacters = [
    ['(', ')'],
    ['[', ']'],
    ['{', '}']
  ];
  const parenMap = new Map(paranCharacters);
  const validParens = paranCharacters.flat();

  for(let i=0; i<lispString.length; i++) {
    var char = lispString[i];

    if(!validParens.includes(char)) {
      continue;
    }

    if (parenMap.has(char)) {
      var paren = parenMap.get(char);
      parenStack.push(paren);
    } else if (char !== parenStack.pop()) {
      return false;
    }
  }

  return parenStack.length === 0;
}

module.exports = isValid;