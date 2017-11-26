<template>
</template>

<script>
  export default {
    props: [
      'eventBus',
      'config'
    ],
    methods: {
      processHttpResponse: function (response) {
        switch(response.status) {
          case 500:
            this.eventBus.$emit('setAlert', {
              show: true,
              message: 'Internal server error...'
            });
            break;
          case 404:
            this.$router.push('/notFound');
            break;
          case 401:
          case 403:
            localStorage.removeItem(`streamrain-${this.config.tenant.name.replace(/\s/g, '')}-session`);
            this.eventBus.$emit('removeVueSession', null);
            this.eventBus.$emit('setAlert', {
              show: true,
              message: 'The session has expired, please log in again'
            });
            break;
          default:
            this.eventBus.$emit('setAlert', {
              show: true,
              message: 'An error has occurred...'
            });
        }
      }
    }
  }
</script>
