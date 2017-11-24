<template>
 <div>
   <video ref="video" controls />
   <div>Tiempo : <span  ref="current">0</span> segundos</div>
 </div>
</template>

<script>
export default {
props: [
      'config',
      'eventBus',
      'session'
    ],
    data () {
      return {
        url: null
      }
    },
created () {
      const i = this;
      const streamId = this.$route.params.streamId;
      const session = this.session;

      this.$http.get(`${this.config.backend}/user/content/${streamId}`,
      {
        headers: {
          'Authorization': session.token
        }
      }).then((response) => {
        console.log(JSON.stringify(response))
        i.vodContent();
      }).catch((response) => {
        console.error(JSON.stringify(response))
        // switch(response.status) {
        //   case 500:
        //     i.updateAlert({
        //       show: true,
        //       message: 'Internal server error'
        //     });
        //     break;
        //   case 404:
        //     i.updateAlert({
        //       show: true,
        //       message: 'Not found'
        //     });
        //     break;
        //   case 401:
        //   case 403:
        //     localStorage.removeItem(`streamrain-${i.config.tenant.name.replace(/\s/g, '')}-session`);
        //     i.eventBus.$emit('removeVueSession', null);
        //     i.updateAlert({
        //       show: true,
        //       message: 'The session has expired, please log in again'
        //     });
        //     break;
        //   default:
        //     updateAlert({
        //       show: true,
        //       message: 'An error has occurred'
        //     });
        // }
      });
    },
 mounted () {
 },
 beforeDestroy () {
   this.vtime = null;
   clearInterval(this.vtime);
 },
methods: {
  vodContent: function() {
    const i = this;
    const config = this.config;
    const streamId = this.$route.params.streamId;
    this.$http.get(`${config.backend}/user/content/view/${streamId}/${i.session.nickname}/`,
    {
      headers: {
        'Authorization': i.session.token
      }
    }).then((response) => {
      console.log(JSON.stringify(response));
      const url = response.body.pathTokenVOD;
      const player = dashjs.MediaPlayer().create();
      player.initialize(i.$refs.video, url, false);
      // player.seek(0);

      i.$refs.current.innerHTML =i.$refs.video.load();
      i.$refs.current.innerHTML =i.$refs.video.play();
      i.vtime = setInterval(function(){i.$refs.current.innerHTML = i.$refs.video.currentTime;},5000)
    }).catch((response) => {
      console.log(JSON.stringify(response));
    });
  }
}
}
</script>
