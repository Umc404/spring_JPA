console.log("boardComment.js connect");
console.log(bnoVal);

document.getElementById("cmtAdd").addEventListener('click',()=>{
    const cmtText = document.getElementById("cmtText");
    const cmtWriter = document.getElementById("cmtWriter");

    console.log(cmtText);
    console.log(cmtWriter);
    if(cmtText.value == null || cmtText.value == '') {
        alert("내용을 입력하세요.");
        cmtText.focus();
        return false;
    }
    let cmtData = {
        bno: bnoVal,
        writer: cmtWriter.innerText,
        content: cmtText.value
    }
    console.log(cmtData);
    postCommentToServer(cmtData).then(result => {
        if(result =='1') {
            alert("댓글 등록 완료");
            cmtText.value = "";
            spreadCommentList(bnoVal);
        }
    })
})

// resp 링크/param 에 저장된 데이터 가져오기
// 나중에 5개씩 더보기처리
async function getCommentListFromServer(bnoVal, page) {
    try {
        const resp = await fetch('/comment/list/'+bnoVal+"/"+page);
        const result = await resp.json();
        return result;
    } catch(error) {
        console.log(error);
    }
}

// 나중에 페이지처리
function spreadCommentList(bnoVal,page=1) {
    getCommentListFromServer(bnoVal, page).then(result => {
        console.log(result);
        const ul = document.getElementById("cmtListArea");

        if(result.content.length > 0) {
            if(page == 1) {
                ul.innerHTML = "";
            }
            for(let cdto of result.content) {
                let li = `<li data-cno="${cdto.cno}" class="list-group-item">`;
                li +=
                li += `<div class="ms-2 me-auto">`;
                li += `<div class="fw-bold">${cdto.writer}</div>`;
                li += `${cdto.content} </div>`;
                li += `<span class="badge text-bg-primary rounded-pill">${cdto.regAt.substr(0,10)}</span>`;
                li += `<button type="button" class="btn btn-primary mod" data-bs-toggle="modal" data-bs-target="#myModal">수정</button>`
                li += `<button type="button" class="btn btn-danger del">삭제</button>`;
                li += `</li>`;
                ul.innerHTML += li;
            }
            // 더보기 버튼.
            let moreBtn = document.getElementById("moreBtn");

            if(page < result.totalPages) {
                moreBtn.style.visibility = "visible";
                moreBtn.dataset.page = page+1;
            } else {
                moreBtn.style.visibility = "hidden";
            }
        } else {
            let li = `<li class="list-group-item">Comment List None.</li>`;
            ul.innerHTML = li;
        }

    })
}


document.addEventListener('click',(e)=> {
    console.log(e.target);

    // 댓글 더보기 버튼
    if(e.target.id == 'moreBtn') {
        spreadCommentList(bnoVal, parseInt(e.target.dataset.page));
    }

    // 댓글 내용 수정요청 버튼
    if(e.target.classList.contains("mod")) {
        let li = e.target.closest('li');

        let modWriter = li.querySelector('.fw-bold').innerText;
        document.getElementById('cmtWriterMod').innerText = modWriter;
        let cmtText = li.querySelector('.fw-bold').nextSibling;
        document.getElementById('cmtTextMod').value = cmtText.nodeValue;

        document.getElementById('cmtModBtn').setAttribute("data-cno", li.dataset.cno);
    }

    // 댓글 수정 데이터 처리 버튼
    if(e.target.id == 'cmtModBtn') {
        let cmtModData = {
            cno: e.target.dataset.cno,
            content: document.getElementById('cmtTextMod').value
        }
        console.log(cmtModData);
        updateCommentToServer(cmtModData).then(result => {
            if( result > 0 ) {
                alert("댓글 수정 성공");
            } else {
                alert("댓글 수정 실패");
            }
            document.querySelector('.btn-close').click();

            spreadCommentList(bnoVal);
        })
    }
    if(e.target.classList.contains("del")) {
        let cno = e.target.closest('li').dataset.cno;
            removeCommentToServer(cno).then(result => {
                if(result > 0) {
                    alert("댓글 삭제 성공");
                } else {
                    alert("댓글 삭제 실패");
                }
                    spreadCommentList(bnoVal);
            })
    }
})

// 비동기식 댓글 저장처리
async function postCommentToServer(cmtData) {
    try {
        const url = "/comment/post";
        const config = {
            method: "post",
            headers: {
                'content-type':'application/json; charset=utf-8'
            },
            body: JSON.stringify(cmtData)
        };
        const resp = await fetch(url, config);
        console.log(resp);
        const result = await resp.text();
        console.log(result);

        return result;
    } catch(error) {
        console.log(error);
    }
}

async function updateCommentToServer(cmtModData) {
    try {
        const url = "/comment/update";
        const config = {
            method: "put",
            headers: {
                'content-type':'application/json; charset=utf-8'
            },
            body: JSON.stringify(cmtModData)
        };
        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;
    } catch(error) {
        console.log(error)
    }
}

async function removeCommentToServer(cno) {
    try {
        const url = "/comment/delete/" + cno;
        const config = {
            method: 'delete'
        };
        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;
    } catch(error) {
        console.log(error);
    }
}