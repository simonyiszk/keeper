function makeBars() {
    let rows = document.getElementsByClassName("plstimebar")
    for (let i=0;i<rows.length;i++){
        let theDiv=document.createElement("div")
        theDiv.classList.add("progress")
        let theProgress = document.createElement("div")
        theProgress.classList.add("progress-bar")
        theDiv.appendChild(theProgress)
        theProgress.innerText=moment(rows[i].dataset['deadline']).fromNow()
        theProgress.role="progressbar"
        let taken = Date.parse(rows[i].dataset['taken'])
        let deadline = Date.parse(rows[i].dataset['deadline'])
        let now = Date.now()
        let percent=((now-taken)/(deadline-taken)*100)+"%"
        theProgress.style.width=percent
        let theTr=document.createElement("tr")
        let theTd=document.createElement("td")
        theTr.title=`Elvitte: ${moment(rows[i].dataset['taken']).format('YYYY-MM-DD hh:mm')}<br>Határidő: ${moment(rows[i].dataset['deadline']).format('YYYY-MM-DD hh:mm')}<br>${parseInt(percent)}%`
        theTr.dataset['placement']="auto"
        theTr.dataset['html']="true"
        theTr.dataset['toggle']="tooltip"
        theTr.appendChild(theTd)
        theTd.appendChild(theDiv)
        theTd.colSpan=69
        rows[i].parentNode.insertBefore(theTr, rows[i].nextSibling);

    }
    $(document).ready(function(){
        $('[data-toggle="tooltip"]').tooltip();
    });
}