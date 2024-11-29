console.log("boardRegister.js Connect");

document.getElementById('trigger').addEventListener('click', ()=>{
    document.getElementById('file').click();
});

const regExp = new RegExp("\.(exe|sh|bat|jar|dll|msi)$");
const maxSize = 1024 * 1024 * 20;           // 20mb

function fileValidation(fileName, fileSize) {
    if(regExp.test(fileName)) {
        return 0;
    } else if(fileSize > maxSize) {
        return 0;
    } else {
        return 1;
    }
}

document.addEventListener('change',(e)=> {
    if(e.target.id == 'file') {
        const fileObject = document.getElementById('file').files;
        console.log(fileObject);



        const fileZone = document.getElementById('fileZone');
        fileZone.innerHTML = "";
        let ul = `<ul class="list-group list-group-flush">`;
        let isOk = 1;

        for(let file of fileObject) {
            let valid = fileValidation(file.name, file.size);
            isOk *= valid;

            ul += `<li class="list-group-item">`;
            ul += `<div class="ms-2 me-auto">`;
            ul += `${valid ? '<div class="fw-bold">업로드 가능</div>':'<div class="fw-bold text-danger">업로드 불가능</div>'}`;
            ul += `${file.name} </div>`;
            ul += `<span class="badge text-bg-${valid ? 'success':'danger'} rounded-pill">
                    ${file.size > 1024 ? ( file.size > 1024*1024 ? Math.round((file.size/(1024*1024))*10)/10 : Math.round((file.size/1024)*10)/10 ) : file.size }
                    ${file.size > 1024 ? ( file.size > 1024*1024 ? "Mb" : "Kb" ) : "Byte"}
                    </span>`;
            // ( file.size > 1024*1024 ? Math.round((file.size / 1024*1024)*10)/10 : Math.round((file.size / 1024)*10)/10 )
            ul += `</li>`;
        }

        ul += `</ul>`;
        fileZone.innerHTML = ul;

        if(isOk == 0) {
            document.getElementById('regBtn').disabled = true;
        }
    }
})

function readURL(input) {
  if (input.files && input.files[0]) {
    var reader = new FileReader();
    reader.onload = function(e) {
      document.getElementById('preview').src = e.target.result;
    };
    reader.readAsDataURL(input.files[0]);
  } else {
    document.getElementById('preview').src = "";
  }
}

async function removeFileToServer(uuid) {
    try {
        const url = "/board/file/" + uuid;
        const config = {
            method: 'delete'
        }
        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;
    } catch(error) {
        console.log(error);
    }
}

/*file upload*/
let sec9 = document.querySelector('#ex9');
let btnUpload = sec9.querySelector('.btn btn-info');
let inputFile = sec9.querySelector('input[type="file"]');
let uploadBox = sec9.querySelector('.upload-box');

/* 박스 안에 Drag 들어왔을 때 */
    uploadBox.addEventListener('dragenter', function(e) {
        console.log('dragenter');
    });

    /* 박스 안에 Drag를 하고 있을 때 */
    uploadBox.addEventListener('dragover', function(e) {
        e.preventDefault();
        console.log('dragover');

        this.style.boarderColor = 'blue';
//        document.getElementById('file').disabled = true;
    });

    /* 박스 밖으로 Drag가 나갈 때 */
    uploadBox.addEventListener('dragleave', function(e) {
        console.log('dragleave');

        this.style.backgroundColor = 'white';
    });

    /* 박스 안에서 Drag를 Drop했을 때 */
    uploadBox.addEventListener('drop', function(e) {
        e.preventDefault();

        console.log('drop');
        this.style.backgroundColor = 'whitegray';

        let files = e.dataTransfer.files; // 드래그된 파일 목록
        console.log(files);

         handleFileUpload(files);
    });

function handleFileUpload(files) {
    const fileZone = document.getElementById('fileZone');
    fileZone.innerHTML = "";
    let ul = `<ul class="list-group list-group-flush">`;
    let isOk = 1;

    /* 사진 미리보기 */


    // 폼에 파일을 추가할 새로운 FileList를 생성하는 DataTransfer 객체 생성
//    let dataTransfer = new DataTransfer();

    for (let file of files) {
        let valid = fileValidation(file.name, file.size);
        isOk *= valid;

        ul += `<li class="list-group-item">`;
        ul += `<div class="ms-2 me-auto">`;
        ul += `${valid ? '<div class="fw-bold">업로드 가능</div>' : '<div class="fw-bold text-danger">업로드 불가능</div>'}`;
        ul += `${file.name} </div>`;

//        if (valid && file.type.startsWith('image/')) {
//            const reader = new FileReader();
//
//            // reader.onload 이벤트 처리 - 이미지 미리보기를 보여준다
//            reader.onload = function (e) {
//                // 각 파일에 대해 고유한 img 태그 추가
//                let imgPreview = `<img src="${e.target.result}" alt="${file.name}" class="preview-img" style="max-width: 100px; max-height: 100px; margin-right: 10px;">`;
//                ul += `<div class="img-preview-container">${imgPreview}</div>`;
//            };
//
//            reader.readAsDataURL(file);  // 파일을 base64로 읽기
//        }

        ul += `<span class="badge text-bg-${valid ? 'success' : 'danger'} rounded-pill">
                    ${file.size > 1024 ? (file.size > 1024 * 1024 ? Math.round((file.size / (1024 * 1024)) * 10) / 10 : Math.round((file.size / 1024) * 10) / 10) : file.size}
                    ${file.size > 1024 ? (file.size > 1024 * 1024 ? "Mb" : "Kb") : "Byte"}
                    </span>`;
        ul += `</li>`;

        // valid한 파일만 dataTransfer 객체에 추가
//        if (valid) {
//            dataTransfer.items.add(file);
//        }
    }

    ul += `</ul>`;
    fileZone.innerHTML = ul;

    // 파일이 유효하다면 서버로 업로드 (폼 제출을 자동으로 하도록)
    if (isOk == 1) {
        // 새로운 FileList를 input[type="file"]에 설정
        let inputFile = document.querySelector('input[type="file"]');
        inputFile.files = new DataTransfer().files;  // 새로운 FileList를 input의 files에 설정
        // 폼을 직접 제출
//        document.querySelector('form').submit();
    } else {
        document.getElementById('regBtn').disabled = true;
    }
}