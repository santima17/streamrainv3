<template>
  <div>
    <div v-if="stream.id && !sending" v-on:mouseleave="loadMyRank()">
      <i v-bind:class="stars.s1.class" v-on:mouseover="paintStars(1)" v-on:click="sendRank(1)"/>
      <i v-bind:class="stars.s2.class" v-on:mouseover="paintStars(2)" v-on:click="sendRank(2)"/>
      <i v-bind:class="stars.s3.class" v-on:mouseover="paintStars(3)" v-on:click="sendRank(3)"/>
      <i v-bind:class="stars.s4.class" v-on:mouseover="paintStars(4)" v-on:click="sendRank(4)"/>
      <i v-bind:class="stars.s5.class" v-on:mouseover="paintStars(5)" v-on:click="sendRank(5)"/>
    </div>
    <div v-if="sending">
      <i class="fa fa-spinner fa-spin text-info" style="font-size"></i>
    </div>
    <div v-if="localRanking">
      <b>Ranking:</b> {{ localRanking }}
    </div>
    <div v-if="stream.id && !localRanking">
      <b>Ranking:</b> Unranked
    </div>
    <streamrain-errorshelper ref="errorshelper"
      :eventBus="eventBus"
      :config="config"
    >
    </streamrain-errorshelper>
  </div>
</template>

<script>
  import ErrorsHelper from '../utils/ErrorsHelper.vue';
  export default {
    props: [
      'session',
      'stream',
      'myRank',
      'postRank',
      'getRank',
      'eventBus',
      'config'
    ],
    components: {
      'streamrain-errorshelper': ErrorsHelper
    },
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
        sending: false,
        myLocalRank: null,
        localRanking: null,
      }
    },
    created () {
      this.localRanking = this.stream.ranking;
    },
    methods: {
      getUpdatedRanking: function () {
        const i = this;
        i.$http.get(i.getRank,
        {
          headers: {
            'Authorization': i.session.token
          }
        }).then((response) => {
          i.updateLocalRanking((response.body).toFixed(1));
        }).catch((response) => {
          console.error(JSON.stringify(response));
          i.$refs.errorshelper.processHttpResponse(response);
        });
      },
      updateSending: function (sending) {
        this.sending = sending;
      },
      updateLocalRanking: function (localRanking) {
        this.localRanking = localRanking;
      },
      updateMyLocalRank: function (myLocalRank) {
        this.myLocalRank = myLocalRank;
      },
      sendRank: function (rank) {
        if (rank === this.myLocalRank) return;
        const i = this;
        i.updateSending(true);
        i.$http.post(i.postRank, {
          contentID: i.stream.id,
          userNickname: i.session.nickname,
          rank
        },
        {
          headers: {
            'Authorization': i.session.token
          }
        }).then((response) => {
          i.paintStars(rank);
          i.updateMyLocalRank(rank);
          i.getUpdatedRanking();
          i.updateSending(false);
        }).catch((response) => {
          i.updateSending(false);
          console.error(JSON.stringify(response));
          i.$refs.errorshelper.processHttpResponse(response);
        });
      },
      loadMyRank: function () {
        this.paintStars(this.myLocalRank || this.myRank);
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

<style>
  .star {
    cursor: pointer;
  }
</style>
