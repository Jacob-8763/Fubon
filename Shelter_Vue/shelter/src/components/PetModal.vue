<template>
    <div ref="exampleModal" class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel"
        aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">請填寫動物資料</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form enctype="multipart/form-data">
                        <div class="mb-3" v-show="isShowInsertButton">
                            <label for="name" class="form-label">收容編號 *</label>
                            <input class="form-control" type="text" name="id" :value="pet.id" required
                                @input="doInput('id', $event)">
                        </div>
                        <div class="mb-3">
                            <label for="age" class="form-label">種類 *</label>
                            <input class="form-control" type="text" name="species" :value="pet.species" required
                                @input="doInput('species', $event)">
                        </div>
                        <div class="mb-3">
                            <label for="department" class="form-label">品種</label>
                            <input class="form-control" type="text" name="breed" :value="pet.breed"
                                @input="doInput('breed', $event)">
                        </div>
                        <div class="mb-3">
                            <label for="position" class="form-label">花色</label>
                            <input class="form-control" type="text" name="color" :value="pet.color"
                                @input="doInput('color', $event)">
                        </div>
                        <div class="mb-3">
                            <label for="position" class="form-label">性別</label>
                            <input class="form-control" type="text" name="gender" maxlength="1" :value="pet.gender"
                                @input="doInput('gender', $event)">
                        </div>
                        <div class="mb-3">
                            <label for="position" class="form-label">狀態 *</label>
                            <input class="form-control" type="text" name="status" :value="pet.status" required
                                @input="doInput('status', $event)">
                        </div>
                        <div class="mb-3">
                            <label for="position" class="form-label">入所時間</label>
                            <input class="form-control" type="datetime-local" name="arrivalDate" :value="pet.arrivalDate"
                                @input="doInput('arrivalDate', $event)">
                        </div>
                        <div class="mb-3">
                            <label for="position" class="form-label">入所原因</label>
                            <input class="form-control" type="text" name="intakeReason" :value="pet.intakeReason"
                                @input="doInput('intakeReason', $event)">
                        </div>
                        <div class="mb-3">
                            <label for="position" class="form-label">離所時間</label>
                            <input class="form-control" type="datetime-local" name="departureDate" :value="pet.departureDate"
                                @input="doInput('departureDate', $event)">
                        </div>
                        <div class="mb-3">
                            <label for="position" class="form-label">領養人</label>
                            <input class="form-control" type="text" name="adopterName" :value="pet.adopterName"
                                @input="doInput('adopterName', $event)">
                        </div>
                        <div class="mb-3">
                            <label for="position" class="form-label">備註</label>
                            <input class="form-control" type="text" name="notes" :value="pet.notes"
                                @input="doInput('notes', $event)">
                        </div>
                        <div class="mb-3">
                            <label for="formFile" class="form-label">上傳圖片</label>
                            <input type="file" class="form-control" accept="image/*" @change="handleFileChange($event)">
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <!-- 顯示 "新增" 按鈕 -->
                    <button type="button" class="btn btn-primary" v-show="isShowInsertButton"
                        @click="emits('insert')">新增</button>
                    <!-- 顯示 "修改" 按鈕 -->
                    <button type="button" class="btn btn-primary" v-show="!isShowInsertButton"
                        @click="emits('update')">修改</button>
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">關閉</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
const props = defineProps(["pet", "isShowInsertButton"])
const emits = defineEmits(["update:pet", "insert", "update"])

function doInput(key, event) {
    emits("update:pet", {
        ...props.pet,
        [key]: event.target.value
    });
}
function handleFileChange(event) {
    const file = event.target.files[0];
    emits("update:pet", {
        ...props.pet,
        file: file
    });
}

import bootstrap from 'bootstrap/dist/js/bootstrap.bundle.min.js';
import { ref, onMounted } from 'vue';

const exampleModal = ref(null);
const exampleObj = ref(null);

onMounted(function () {
    exampleObj.value = new bootstrap.Modal(exampleModal.value, { backdrop: 'static' });
});

function showModal() {
    exampleObj.value.show();
}

function hideModal() {
    exampleObj.value.hide();
}

defineExpose({
    showModal, hideModal
});
</script>

<style scoped>
.form-control {
    width: 100%;
}

.form-select {
    width: 100%;
}
</style>
