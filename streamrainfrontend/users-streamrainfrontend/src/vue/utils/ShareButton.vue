<template>
  <div class="text-right">
    <div>
      <a v-on:click="getAllUsers()" data-toggle="modal" data-target="#shareModal" class="text-info" role="button">
        <i class="lead glyphicon glyphicon-share">
        </i>
      </a>
    </div>
    <div class="modal fade" id="shareModal" role="dialog" aria-labelledby="shareModal" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <p class="lead modal-title text-left" id="myModalLabel">Share this content!</p>
          </div>
          <div class="modal-body text-left">
            <ul class="list-inline">
              <li v-for="(user, index) in allUsers" :key="index">
                <div class="checkbox">
                  <label><input type="checkbox" v-on:click="clickCheck" :value="user.nickname">{{ user.nickname }}</label>
                </div>
              </li>
            </ul>
            <p :hidden="!shared" class="text-success text-center">Shared!</p>
          </div>
          <div class="modal-footer">
            <button :disabled="counter > 0" v-on:click="share()" type="button" class="btn btn-info">
              {{ buttonText }} <i v-if="counter > 0"class="fa fa-spinner fa-spin" style="font-size"></i>
            </button>
          </div>
        </div>
      </div>
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
      'contentId',
      'eventBus',
      'config'
    ],
    components: {
      'streamrain-errorshelper': ErrorsHelper
    },
    data () {
      return {
        showModal: false,
        allUsers: null,
        usersToShare: [],
        counter: 0,
        buttonText: 'Share',
        shared: false
      }
    },
    methods: {
      updateShared: function (shared) {
        this.shared = shared;
      },
      updateCounter: function (counter) {
        this.updateShared(false);
        this.counter = counter;
        this.buttonText = 'Sharing'
      },
      decCounter: function () {
        this.counter = this.counter - 1;
        // Por las dudas.
        if (this.counter < 0) {
          this.counter = 0;
        }
        if (this.counter === 0) {
          this.updateShared(true);
          this.buttonText = 'Share'
        } 
      },
      updateAllUsers: function (allUsers) {
        this.allUsers = allUsers;
      },
      clickCheck: function (event) {
        const username = event.target.value;
        const checked = event.target.checked;
        if (checked) {
          let index = this.usersToShare.indexOf(username);
          if (index < 0) {
            this.usersToShare.push(username);
          }
        } else {
          let index = this.usersToShare.indexOf(username);
          if (index > -1) {
            this.usersToShare.splice(index, 1);
          }
        }
      },
      getAllUsers: function () {
        const i = this;
        i.$http.get(`${i.config.backend}/user`,
        {
          headers: {
            'Authorization': i.session.token
          }
        }).then((response) => {
          i.updateAllUsers(response.body);
        }).catch((response) => {
          console.error(JSON.stringify(response));
          i.$refs.errorshelper.processHttpResponse(response);
        });
      },
      share: function () {
        if (this.usersToShare.length <= 0) return;
        this.updateCounter(this.usersToShare.length);
        const i = this;
        const now = new Date();
        this.usersToShare.forEach((user) => {
          i.$http.post(`${i.config.backend}/user/content/shareContent`,
          {
            contentId: i.contentId,
            usersByDestinationUserId: user,
            usersByUserId: i.session.nickname,
            date: now
          },
          {
            headers: {
              'Authorization': i.session.token
            }
          }).then((response) => {
            i.decCounter();
            console.log('enviado...');
          }).catch((response) => {
            i.decCounter();
            console.error(JSON.stringify(response));
          });
        });
      }
    }
  }
</script>

<style>
  .modal-open {
    overflow-y: scroll;
  }
</style>
