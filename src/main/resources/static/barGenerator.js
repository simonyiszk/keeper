function makeBars() {
    let rows = document.getElementsByClassName("plstimebar")
    for (let i=0;i<rows.length;i++){
        let theDiv=document.createElement("div")
        theDiv.classList.add("progress")
        let theProgress = document.createElement("div")
        theProgress.classList.add("progress-bar")
        theDiv.appendChild(theProgress)
        theProgress.role="progressbar"
        let taken = Date.parse(rows[i].dataset['taken'])
        let deadline = Date.parse(rows[i].dataset['deadline'])
        let now = Date.now()
        let percent=((now-taken)/(deadline-taken)*100)+"%"
        theProgress.style.width=percent
        let theTr=document.createElement("tr")
        let theTd=document.createElement("td")
        theTr.appendChild(theTd)
        theTd.appendChild(theDiv)
        theTd.colSpan=6
        rows[i].parentNode.insertBefore(theTr, rows[i].nextSibling);

    }
}