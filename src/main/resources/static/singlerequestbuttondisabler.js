(async function checkreqestbutton(){
    let deviceId = document.getElementById("deviceid").innerText
    let requests = JSON.parse(await (await fetch(`${window.location.origin}/ajax/myReqs`)).text())
    requests.forEach(request=>{
        if (request.deviceEntity === parseInt(deviceId)){
            let requestbutton=document.getElementById("requestButton");
            requestbutton.classList.add("disabled");
        }
    })
    return requests;
})()
