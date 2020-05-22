var semcurrentpage = 0
var semlast = false
var semfirst = true

async function loaddevicesroot(page, pagesize) {
    let resp = await fetch(window.location.origin+"/ajax/devices?page="+page+"&pageSize="+parseInt(pagesize,10));
    let res = JSON.parse(await resp.text());
    let table = document.getElementById("tablebody");
    table.innerHTML="";
    for (device of res.content){
        let neu = document.createElement("tr")
        neu.appendChild(document.createElement("th"))
        neu.appendChild(document.createElement("td"))
        neu.appendChild(document.createElement("td"))
        neu.children[0].innerText=device.id;
        neu.children[1].innerText=device.name;
        neu.children[2].innerText=device.description;
        table.appendChild(neu);
    }
    semlast=res.last;
    semfirst=res.first;
}