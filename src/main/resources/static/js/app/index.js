var main = {
    init : function () {
        let _this = this;

        let saveButton = document.getElementById("btn-save");
        if (saveButton != null){
            saveButton.addEventListener("click", () => {
                _this.save()
            });
        }
        
        let updateButton = document.getElementById("btn-update");
        if (updateButton != null){
            updateButton.addEventListener("click", () => {
                _this.update()
            });
        }

        let deleteButton = document.getElementById("btn-delete");
        if (deleteButton != null){
            deleteButton.addEventListener("click", () => {
                _this.delete()
            });
        }

        
    },

    save : function () {
        let data = {
            title: document.getElementById("title").value,
            author: document.getElementById("author").value,
            content: document.getElementById("content").value
        };

        let http = new XMLHttpRequest();
        http.open("POST", "/api/v1/posts", true);
        http.setRequestHeader("Content-type", "application/json; charset=utf-8");
        
        http.onreadystatechange = () => {
            if(http.readyState == 4 && (http.status == 200 || http.status == 201)) {
                alert("글이 등록되었습니다");
                window.location.href = "/";
            }
        }
        http.onerror = (e) => {
            alert(e);
        }

        http.send(JSON.stringify(data));
    },

    update : function () {
        let id = document.getElementById("id").value; 
        let data = {
            title: document.getElementById("title").value,
            content: document.getElementById("content").value
        };

        let http = new XMLHttpRequest();
        http.open("PUT", "/api/v1/posts/"+id, true);
        http.setRequestHeader("Content-type", "application/json; charset=utf-8");
        
        http.onreadystatechange = () => {
            if(http.readyState == 4 && (http.status == 200 || http.status == 201)) {
                alert("글이 수정되었습니다");
                window.location.href = "/";
            }
        }
        http.onerror = (e) => {
            alert(e);
        }

        http.send(JSON.stringify(data));
    },

    delete : function () {
        let id = document.getElementById("id").value;

        let http = new XMLHttpRequest();
        http.open("DELETE", "/api/v1/posts/"+id, true);
        http.setRequestHeader("Content-type", "application/json; charset=utf-8");
        
        http.onreadystatechange = () => {
            if(http.readyState == 4 && (http.status == 200 || http.status == 201)) {
                alert("글이 삭제되었습니다");
                window.location.href = "/";
            }
        }
        http.onerror = (e) => {
            alert(e);
        }

        http.send(null);
    }
}

main.init();
