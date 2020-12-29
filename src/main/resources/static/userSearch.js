
async function loadUsers(page) {
    let searchbox=document.getElementById("searchTerm")
    let resp = await fetch(`${window.location.origin}/ajax/usersearch/${page}/${itemsPerPage}?term=${searchbox.value}`);
    let res = JSON.parse(await resp.text());
    let table = document.getElementById("tablebody");
    table.innerHTML="";
    for (user of res.content) {
        let neu = document.createElement("tr")
        neu.appendChild(document.createElement("th"))
        neu.appendChild(document.createElement("td"))
        neu.appendChild(document.createElement("td"))
        neu.children[0].innerText = user.email
        neu.children[1].appendChild(document.createElement("span"))
        neu.children[1].children[0].innerText = user.fullName
        if (user.id === userid)
            neu.classList.add("table-success")
        if (user.note) {
            let noteSpan = document.createElement("span")
            noteSpan.style.fontStyle = "italic"
            noteSpan.innerHTML = `<br>Note: ${user.note}`
            neu.children[1].appendChild(noteSpan)
        }
        let button = {
            id: undefined,
            text: "Kölcsönad",
            link: `${window.location.href}/${user.id}`,
            class: "btn-success",
            parent: neu.children[2],
            disableifout: false
        }
        makeButton(button)

        table.appendChild(neu)
    }
    semlast=res.last;
    semfirst=res.first;
    let pagiButtons=[
        {id:"prevbutton", bindbool:semfirst},
        {id:"nextbutton", bindbool:semlast}
    ]
    pagiButtons.forEach(btn=>{
        let element=document.getElementById(btn.id)
        if(btn.bindbool){
            element.classList.remove("active")
        } else {
            element.classList.add("active")
        }
    })
    document.getElementById("pageCount").innerText=`${semcurrentpage+1}/${res.totalPages}`
}
