<template>
  <div>
    <div v-if="stream.id && !sendingRank" v-on:mouseleave="loadMyRank(myRank)">
      <i v-bind:class="stars.s1.class" v-on:mouseover="paintStars(1)" v-on:click="sendRank(1)"/>
      <i v-bind:class="stars.s2.class" v-on:mouseover="paintStars(2)" v-on:click="sendRank(2)"/>
      <i v-bind:class="stars.s3.class" v-on:mouseover="paintStars(3)" v-on:click="sendRank(3)"/>
      <i v-bind:class="stars.s4.class" v-on:mouseover="paintStars(4)" v-on:click="sendRank(4)"/>
      <i v-bind:class="stars.s5.class" v-on:mouseover="paintStars(5)" v-on:click="sendRank(5)"/>
    </div>
    <div v-if="sendingRank">
      <i class="fa fa-spinner fa-spin text-info" style="font-size"></i>
    </div>
    <div v-if="stream.ranking">
      <b>Ranking:</b> {{ stream.ranking }}
    </div>
    <div v-if="stream.id && !stream.ranking">
      <b>Ranking:</b> Unranked
    </div>
  </div>
</template>

<script>
  export default {
    props: [
      'session',
      'stream',
      'myRank',
      'postRank',
      'getRank'
    ],
    data () {
      return {
        stars: {
          s1: {
            class: 'star glyphicon glyphicon-star',
            painted: false
          },
          s2: {
            class: 'star glyphicon glyphicon-star',
            painted: false
          },
          s3: {
            class: 'star glyphicon glyphicon-star',
            painted: false
          },
          s4: {
            class: 'star glyphicon glyphicon-star',
            painted: false
          },
          s5: {
            class: 'star glyphicon glyphicon-star',
            painted: false
          }
        },
        sendingRank: false
      }
    },
    methods: {
      setSendingRank: function (sendingRank) {
        this.sendingRank = sendingRank;
      },
      sendRank: function (rank) {
        if (rank === this.myRank) return;
        const i = this;
        i.setSendingRank(true);
        i.$http.post(i.postRank, {rank},
        {
          headers: {
            'Authorization': i.session.token
          }
        }).then((response) => {
          i.paintStars(rank);
          i.setSendingRank(false);
        }).catch((response) => {
          console.error(JSON.stringify(response));
          // TODO: mostrar una alerta.
        });
      },
      loadMyRank: function (myRank) {
        this.myRank = myRank;
        this.paintStars(myRank);
      },
      paintStars: function (star) {
        switch (star) {
          default:
          case 0:
            this.stars.s1.class = 'star glyphicon glyphicon-star';
            this.stars.s1.painted = false;
            this.stars.s2.class = 'star glyphicon glyphicon-star';
            this.stars.s2.painted = false;
            this.stars.s3.class = 'star glyphicon glyphicon-star';
            this.stars.s3.painted = false;
            this.stars.s4.class = 'star glyphicon glyphicon-star';
            this.stars.s4.painted = false;
            this.stars.s5.class = 'star glyphicon glyphicon-star';
            this.stars.s5.painted = false;
            break;
          case 1:
            this.stars.s1.class = 'star glyphicon glyphicon-star text-info';
            this.stars.s1.painted = true;
            this.stars.s2.class = 'star glyphicon glyphicon-star';
            this.stars.s2.painted = false;
            this.stars.s3.class = 'star glyphicon glyphicon-star';
            this.stars.s3.painted = false;
            this.stars.s4.class = 'star glyphicon glyphicon-star';
            this.stars.s4.painted = false;
            this.stars.s5.class = 'star glyphicon glyphicon-star';
            this.stars.s5.painted = false;
            break;
          case 2:
            this.stars.s1.class = 'star glyphicon glyphicon-star text-info';
            this.stars.s1.painted = true;
            this.stars.s2.class = 'star glyphicon glyphicon-star text-info';
            this.stars.s2.painted = true;
            this.stars.s3.class = 'star glyphicon glyphicon-star';
            this.stars.s3.painted = false;
            this.stars.s4.class = 'star glyphicon glyphicon-star';
            this.stars.s4.painted = false;
            this.stars.s5.class = 'star glyphicon glyphicon-star';
            this.stars.s5.painted = false;
            break;
          case 3:
            this.stars.s1.class = 'star glyphicon glyphicon-star text-info';
            this.stars.s1.painted = true;
            this.stars.s2.class = 'star glyphicon glyphicon-star text-info';
            this.stars.s2.painted = true;
            this.stars.s3.class = 'star glyphicon glyphicon-star text-info';
            this.stars.s3.painted = true;
            this.stars.s4.class = 'star glyphicon glyphicon-star';
            this.stars.s4.painted = false;
            this.stars.s5.class = 'star glyphicon glyphicon-star';
            this.stars.s5.painted = false;
            break;
          case 4:
            this.stars.s1.class = 'star glyphicon glyphicon-star text-info';
            this.stars.s1.painted = true;
            this.stars.s2.class = 'star glyphicon glyphicon-star text-info';
            this.stars.s2.painted = true;
            this.stars.s3.class = 'star glyphicon glyphicon-star text-info';
            this.stars.s3.painted = true;
            this.stars.s4.class = 'star glyphicon glyphicon-star text-info';
            this.stars.s4.painted = true;
            this.stars.s5.class = 'star glyphicon glyphicon-star';
            this.stars.s5.painted = false;
            break;
          case 5:
            this.stars.s1.class = 'star glyphicon glyphicon-star text-info';
            this.stars.s1.painted = true;
            this.stars.s2.class = 'star glyphicon glyphicon-star text-info';
            this.stars.s2.painted = true;
            this.stars.s3.class = 'star glyphicon glyphicon-star text-info';
            this.stars.s3.painted = true;
            this.stars.s4.class = 'star glyphicon glyphicon-star text-info';
            this.stars.s4.painted = true;
            this.stars.s5.class = 'star glyphicon glyphicon-star text-info';
            this.stars.s5.painted = true;
            break;
        }
      }
    }
  }
</script>
