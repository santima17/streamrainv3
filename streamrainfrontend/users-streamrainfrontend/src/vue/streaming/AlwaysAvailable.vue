<template>
 <div>
   <video ref="video" controls />
   <div>Tiempo : <span  ref="current">0</span> segundos</div>
 </div>
</template>

<script>
export default {
 data () {
   return {
     vtime: null
   }
 },
 mounted () {
   const i = this;
   const url = "http://streamrainvod-streamrain.193b.starter-ca-central-1.openshiftapps.com/dash/devito360p.mp4/manifest.mpd?st=mG0q4sTct7wIU4psxPnujA&e=1511519380";
   const player = dashjs.MediaPlayer().create();
   player.initialize(i.$refs.video, url, false);
   player.seek(30);        

   i.$refs.current.innerHTML =i.$refs.video.load();
   i.$refs.current.innerHTML =i.$refs.video.play();

        this.vtime = setInterval(function(){i.$refs.current.innerHTML = i.$refs.video.currentTime;},5000)
 },
 beforeDestroy () {
   this.vtime = null;
   clearInterval(this.vtime);
 }
}
</script>
