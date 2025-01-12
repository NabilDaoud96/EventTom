<!-- ConfirmationModal.vue -->
<template>
  <Teleport to="body">
    <div v-if="modelValue" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
      <div class="bg-white rounded-lg p-6 max-w-sm w-full mx-4">
        <h3 class="text-lg font-medium text-gray-900 mb-4">{{ title }}</h3>
        <p class="text-gray-500 mb-6">{{ message }}</p>
        <div class="flex justify-end space-x-3">
          <button
              @click="cancel"
              class="px-4 py-2 text-gray-700 bg-gray-100 rounded-md hover:bg-gray-200"
          >
            {{ cancelText }}
          </button>
          <button
              @click="confirm"
              :class="confirmButtonClass"
          >
            {{ confirmText }}
          </button>
        </div>
      </div>
    </div>
  </Teleport>
</template>

<script>
export default {
  name: 'ConfirmationModal',
  props: {
    modelValue: {
      type: Boolean,
      required: true
    },
    title: {
      type: String,
      default: 'Confirm Action'
    },
    message: {
      type: String,
      default: 'Are you sure you want to proceed? This action cannot be undone.'
    },
    confirmText: {
      type: String,
      default: 'Confirm'
    },
    cancelText: {
      type: String,
      default: 'Cancel'
    },
    type: {
      type: String,
      default: 'danger',
      validator: (value) => ['danger', 'warning', 'success'].includes(value)
    }
  },
  computed: {
    confirmButtonClass() {
      const baseClasses = 'px-4 py-2 text-white rounded-md';
      const typeClasses = {
        danger: 'bg-red-500 hover:bg-red-600',
        warning: 'bg-yellow-500 hover:bg-yellow-600',
        success: 'bg-green-500 hover:bg-green-600'
      };
      return `${baseClasses} ${typeClasses[this.type]}`;
    }
  },
  methods: {
    confirm() {
      this.$emit('update:modelValue', false);
      this.$emit('confirm');
    },
    cancel() {
      this.$emit('update:modelValue', false);
      this.$emit('cancel');
    }
  }
}
</script>