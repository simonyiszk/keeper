function replaceDatesWithNiceStuff() {
    let a = document.getElementsByClassName("replace");
    for (let i=0;i<a.length;i++){
        moment().locale("hu");
        let text = a[i].innerText;
        if (text) {
            a[i].innerText = moment(a[i].innerText).fromNow();
        }
    }
}
