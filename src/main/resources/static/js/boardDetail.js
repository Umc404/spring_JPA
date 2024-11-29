console.log("BoardDetail js connect");

document.getElementById('listBtn').addEventListener('click',()=> {
    location.href="/board/list";
})

document.getElementById('modBtn').addEventListener('click',()=> {
    document.getElementById('title').readOnly = false;
    document.getElementById('content').readOnly = false;
    document.getElementById('content').readOnly = false;
    document.getElementById('trigger').disabled = false;

    // 버튼 생성
    let modBtn = document.createElement("button");
    modBtn.setAttribute("type","submit");
    modBtn.classList.add("btn","btn-outline-warning");
    modBtn.innerText="Submit";

    // 추가
    document.getElementById("modForm").appendChild(modBtn);
    document.getElementById("modBtn").remove();
    document.getElementById("delBtn").remove();

    let fileDelBtn = document.querySelectorAll(".file-x");
        console.log(fileDelBtn);
        for(let delBtn of fileDelBtn) {
            delBtn.disabled = false;
        }
})

document.getElementById("delBtn").addEventListener('click', ()=>{
    document.getElementById('delForm').submit();
})

document.addEventListener('click', (e)=>{
    // console.log(e.target);

    if(e.target.classList.contains('file-x')) {
        let uuid = e.target.dataset.uuid;
        removeFileToServer(uuid).then(result => {
            if(result =='1') {
                alert("파일삭제 성공");
                e.target.closest('li').remove();
            }
        })
    }
})