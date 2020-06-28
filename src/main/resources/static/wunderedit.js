function wunderedit(btnid) {

    let btn=document.getElementById(btnid)

    if(btn.children[0].classList.contains("fa-pen")){
        wunder_prepare(btn)
    } else {
        wunder_save(btn)
    }

    btn.children[0].classList.toggle("fa-pen")
    btn.children[0].classList.toggle("fa-check")

    return 1;
}

function wunder_prepare(btn) {
    let num = parseInt(btn.id)
    let theInput=document.createElement("input")
    let theP=document.getElementById(num+"p")
    theInput.value=theP.innerText
    theInput.type="text"
    theInput.classList.add("form-control")
    theInput.id=num+"inp"
    theInput.placeholder="Eltörte xd"
    theInput.onkeyup=handleEnter
    theP.replaceWith(theInput)
}

async function wunder_save(btn) {
    let loanId = parseInt(btn.id)
    let theInput=document.getElementById(loanId+"inp")
    let theP=document.createElement("p")
    let note = theInput.value
    theP.innerText=note
    theP.id=loanId+"p"
    theP.style="margin:auto;";
    let res = {num: loanId,resStr: note}
    theInput.replaceWith(theP)
    await fetch(`${window.location.origin}/ajax/loanNote`,{
        method: 'POST',
        headers:{
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(res)
    })
}

function handleEnter(event) {
    if (event.keyCode===13){
        event.preventDefault()
        document.getElementById(parseInt(this.id)+'btn').click()
    }
}