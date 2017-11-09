<template>
</template>

<script>
  export default {
    props: [
      'eventBus',
      'selectedServer'
    ],
    created () {
      const i = this;
      this.eventBus.$on('getJanusServerInfo', () => {
        this.getJanusServerInfo();
      }, this);
      this.eventBus.$on('getJanusAuthTokens', () => {
        this.getJanusAuthTokens();
      }, this);
    },
    methods: {
      getJanusServerInfo: function () {
        this.$http.get(`${this.selectedServer.admin_url}/info`)
        .then((result) => { 
          this.eventBus.$emit('setJanusServerInfo', result.body);
        }, this);
      },
      getJanusAuthTokens: function () {
        const request = {
          janus: 'list_tokens',
          transaction: randomString(12),
          admin_secret: this.selectedServer.admin_secret
        };
        this.$http.post(this.selectedServer.admin_url, request)
        .then((result) => { 
          this.eventBus.$emit('setJanusAuthTokens', result.body.data.tokens);
        }, this)
        .catch((error) => {
          console.error(JSON.stringify(error));
        });
      }
    }
  }
  function randomString(len) {
    let charSet = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    let randomString = '';
    for (let i = 0; i < len; i++) {
      let randomPoz = Math.floor(Math.random() * charSet.length);
      randomString += charSet.substring(randomPoz,randomPoz+1);
    }
    return randomString;
  }
</script>
