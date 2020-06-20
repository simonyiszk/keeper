var semcurrentpage = 0
var semlast = false
var semfirst = true
var reqdItems = null
async function getReqdItems() {
    let resp = await fetch(`${window.location.origin}/user/myRequestIds`)
    reqdItems = JSON.parse(await resp.text());
}

var itemsPerPage = localStorage.getItem("semItemsPerPage")||"10";

// Kézműves data binding. Majd írok angulart is, most ez van. Amúgy ha akarsz segíteni, várlak szeretettel

function makeButton(button){
    let theA=document.createElement("a")
    theA.style.cssText="margin:auto;"
    let theButton = document.createElement("button")
    theButton.classList="btn "+button.class
    if(button.text) {
        theButton.innerText = button.text
    } else {
        let i = document.createElement("i")
        i.classList.add("fas")
        i.classList.add(button.i)
        theButton.appendChild(i)
    }
    if (reqdItems.includes(button.id)){
        theButton.disabled=true
        theButton.classList.add("btn-secondary")
    }
    theA.appendChild(theButton)
    theA.href=button.link
    button.parent.appendChild(theA)
}

function setItemsPerPage(number){
    itemsPerPage=number;
    localStorage.setItem("semItemsPerPage", number);
    semcurrentpage=0
    loaddevicesroot(0)
}

async function loaddevicesroot(page) {
    if (reqdItems == null){
        await getReqdItems()
    }
    let searchbox=document.getElementById("searchTerm")
    let resp = await fetch(`${window.location.origin}/ajax/devicesearch/${page}/${itemsPerPage}?term=${searchbox.value}`);
    let res = JSON.parse(await resp.text());
    let table = document.getElementById("tablebody");
    table.innerHTML="";
    for (device of res.content){
        let neu = document.createElement("tr")
        neu.appendChild(document.createElement("th"))
        neu.appendChild(document.createElement("td"))
        let kolcsGomb = document.createElement("td")
        kolcsGomb.appendChild(document.createElement("div"))
        kolcsGomb.children[0].style.cssText="display: flex;flex-direction:column"
        neu.appendChild(kolcsGomb)
        if (admin) {
            let newChild = document.createElement("td")
            newChild.appendChild(document.createElement("div"))
            newChild.children[0].style.cssText="display: flex;flex-direction:column"
            neu.appendChild(newChild)
        }
        neu.children[0].innerText=device.name;
        neu.children[1].innerText=device.description;

        let buttons=[]

        if (admin){
            buttons=[
                {id: device.id, text:"Gib", link:`/loan/new/${device.id}`, class:"btn-primary", parent:neu.children[2].children[0]},
                {id: device.id, text:"Yoink",link:`/loan/new/${device.id}/${userid}`, class: "btn-success", parent:neu.children[2].children[0]},
                {id: device.id, i:"fa-pen",link:`/device/edit/${device.id}`, class: "btn-secondary", parent:neu.children[3].children[0]},
                {id: device.id, i:"fa-trash",link:`/device/delete/${device.id}`, class: "btn-danger", parent:neu.children[3].children[0]}
            ]
        } else {
            buttons=[
                    {id: device.id, text:"Rekvesztálás",link:`loan/request/${device.id}`, class: "btn-primary", parent:neu.children[2]}
                    ]
        }
        buttons.forEach(makeButton)
        table.appendChild(neu);
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

//TODO knockout
async function searchdevices() {
    let searchbox=document.getElementById("alma")
    let resp = await fetch(window.location.origin+"/ajax/devicesearch?term="+alma.value);
    let res = JSON.parse(await resp.text());
    let resdiv = document.getElementById("results");
    resdiv.innerHTML="";
    for (device of res){

        let neu = document.createElement("a")
        neu.href="/loan/new/"+device.id;
        neu.innerText=device.name+" - "+device.description;
        neu.classList="list-group-item list-group-item-action"
        resdiv.appendChild(neu);
    }
}

function pront(){
    let alma=document.getElementById("alma")
    console.log(alma.value)
}