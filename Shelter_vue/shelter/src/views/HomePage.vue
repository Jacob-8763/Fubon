<template>
    <h1>動物收容資料管理</h1>
    <div style="margin: 10px;">
        <div>
            <button type="button" class="btn btn-primary" @click="openModal('insert')" style="display: inline-block; margin-right: 10px;">新增入所資料</button>
            <input type="text" class="form-control" placeholder="請輸入動物種類" @input="callFindByInput($event)" style="display: inline-block; width: 300px;">
        </div>
        <table class="table" v-if="pets.length > 0" style="margin-top: 10px;">
            <thead style="text-align: center;">
                <tr>
                    <th style="width: 150px;">收容編號</th>
                    <th>照片</th>
                    <th>種類</th>
                    <th>品種</th>
                    <th>建檔時間</th>
                    <th>入所時間</th>
                    <th>離所時間</th>
                    <th>領養人</th>
                    <th style="width: 300px;">操作</th>
                </tr>
            </thead>
            <tbody>
                <pet-table v-for="pet in pets" :key="pet.id" :pet="pet" @delete="callDelete" @open-update="openModal">
                </pet-table>
            </tbody>
        </table>
        <!-- 顯示錯誤訊息 -->
        <div v-else style="margin-top: 10px;">
            <p>{{ errorMsg }}</p>
        </div>

        <!-- PetModal: 用來新增或修改動物資料 -->
        <pet-modal ref="petModal" v-model:pet="pet" :is-show-insert-button="isShowInsertButton" @insert="callCreate"
            @update="callUpdate">
        </pet-modal>
    </div>
</template>
    
<script setup>
import { onMounted, ref } from 'vue';
import axiosapi from '@/plugins/axios';
import Swal from 'sweetalert2';

import PetTable from '@/components/PetTable.vue';
import PetModal from '@/components/PetModal.vue';

const pets = ref([]);  // 動物列表
const pet = ref({});   // 當前動物資料（新增或編輯）
const isShowInsertButton = ref(true);  // 是否顯示新增按鈕
const petModal = ref(null);  // 存取emp-modal元件的參考
const errorMsg = ref("");  // 儲存錯誤訊息

// 開啟 modal 彈窗，根據不同的操作（新增或編輯）
function openModal(action, id) {
    if (action === 'insert') {
        isShowInsertButton.value = true;  // 顯示新增按鈕
        pet.value = {};  // 清空當前動物資料
    } else if (action === 'edit') {
        isShowInsertButton.value = false;  // 隱藏新增按鈕，顯示更新按鈕
        callFindById(id);  // 根據id查找動物資料
    }

    // 開啟彈窗
    petModal.value.showModal();
}

// 查找所有動物資料
function callFind() {
    axiosapi.get("/pets/find")
    .then(function(response) {
        console.log("callFind response", response);
        pets.value = response.data;
    })
    .catch(function(error) {
        console.log("callFind error", error);
        errorMsg.value = error.response.data // 設置錯誤訊息
    });
}

// 條件查詢
function callFindByInput(event){
    const body = event.target.value

    if(body.trim().length === 0){
        callFind();
        pets.value = [];  // 清空動物資料
    } else {
        axiosapi.post("/pets/findSpecies", body ,
            {
            headers: {
                "Content-Type": "application/json", // 即使是純文字，也加上正確的 Content-Type
            }
        })
        .then(function(response){
            console.log("callFindByInput response", response);
            pets.value = Array.isArray(response.data) ? response.data : [response.data];
        })
        .catch(function(error){
            console.log("callFindByInput error", error);
            pets.value = [];
            errorMsg.value = error.response.data // 設置錯誤訊息
        });
    }
}

// 查找單一動物資料（編輯時使用）
function callFindById(id) {
    axiosapi.get(`/pets/find/${id}`)
    .then(function(response) {
        console.log("callFindById response", response);
        pet.value = response.data;  // 設置動物資料
    })
    .catch(function(error) {
        console.log("callFindById error", error);
        Swal.fire({
            text: error.response.data,
            icon: "error",
        });
    });
}

// 刪除動物
function callDelete(id) {
    Swal.fire({
        title: "是否確認刪除？",
        icon: "question",
        confirmButtonText: "確定",
        cancelButtonText: "取消",
        showCancelButton: true,
        showCloseButton: true
    }).then(function(result) {
        if (result.isConfirmed) {
            axiosapi.delete(`/pets/${id}`)
            .then(function(response) {
                console.log("刪除成功", response);
                Swal.fire({
                title: "刪除成功",
                icon: "success"
                });
                pets.value = [];
                callFind();  // 刪除成功後重新載入動物資料
            })
            .catch(function(error) {
                console.log("刪除失敗", error);
                Swal.fire({
                    text: error.response.data,
                    icon: "error",
                });
            });
        }
    });
}

// 更新動物資料
function callUpdate() {
    const formData = new FormData();

    if(pet.value.id) formData.append("id", pet.value.id);
    if(pet.value.species) formData.append("species", pet.value.species);
    if(pet.value.breed) formData.append("breed", pet.value.breed);
    if(pet.value.color) formData.append("color", pet.value.color);
    if(pet.value.gender) formData.append("gender", pet.value.gender);
    if(pet.value.status) formData.append("status", pet.value.status);
    if(pet.value.arrivalDate) formData.append("arrivalDate", pet.value.arrivalDate);
    if(pet.value.intakeReason) formData.append("intakeReason", pet.value.intakeReason);
    if(pet.value.departureDate) formData.append("departureDate", pet.value.departureDate);
    if(pet.value.adopterName) formData.append("adopterName", pet.value.adopterName);
    if(pet.value.notes) formData.append("notes", pet.value.notes);
    if (pet.value.file) {
        formData.append("file", pet.value.file);
    }

    axiosapi.put("/pets",  formData, {
        headers: {
        "Content-Type": "multipart/form-data"
        }
    }).then(function(response) {
        console.log("callModify response", response);
        Swal.fire({
        text: "修改成功",
        icon: "success",
        }).then(function (result) {
            petModal.value.hideModal();
            location.reload();
            // callFind();
        });
    })
    .catch(function(error) {
        console.log("更新失敗", error);
        Swal.fire({
            text: error.response.data,
            icon: "error",
        });
    });
}

// 新增動物
function callCreate() {
    Swal.fire({
        text: "Loading......",
        showConfirmButton: false,
        allowOutsideClick: false,
    });

    const formData = new FormData();

    if(pet.value.id) formData.append("id", pet.value.id);
    if(pet.value.species) formData.append("species", pet.value.species);
    if(pet.value.breed) formData.append("breed", pet.value.breed);
    if(pet.value.color) formData.append("color", pet.value.color);
    if(pet.value.gender) formData.append("gender", pet.value.gender);
    if(pet.value.status) formData.append("status", pet.value.status);
    if(pet.value.arrivalDate) formData.append("arrivalDate", pet.value.arrivalDate);
    if(pet.value.intakeReason) formData.append("intakeReason", pet.value.intakeReason);
    if(pet.value.notes) formData.append("notes", pet.value.notes);
    if (pet.value.file) {
        formData.append("file", pet.value.file);
    }

    axiosapi.post("/pets",  formData, {
        headers: {
            "Content-Type": "multipart/form-data"
        }
    }).then(function(response) {
        console.log("callCreate response", response);
        Swal.fire({
            text: "新增成功",
            icon: "success",
        }).then(function(result) {
            petModal.value.hideModal();
            callFind();
        });
    }).catch(function(error) {
        Swal.fire({
            text: error.response.data,
            icon: "error",
        });
    });
}

// 讓組件載入時先取得動物資料
onMounted(function () {
    callFind();
});
</script>
    
<style scoped>
h1{
    text-align: center;
    padding: 20px 0 20px 0;
}   
</style>