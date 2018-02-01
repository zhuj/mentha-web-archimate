

export const inputFocus = (input) => {
  if (input) {
    input.select();
    input.focus();
  }
};

export const buildKeyPress = (commit, rollback) => (event) => {
  const keyCode = event.keyCode;
  if (keyCode === 27) {
    rollback();
  } else if (keyCode === 13) {
    const ctrl = (event.shiftKey || event.ctrlKey);
    if (!ctrl) {
      const title = event.target.value;
      event.stopPropagation();
      commit(title);
    }
  }
};
