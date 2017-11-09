<template>
  <div class="container-fluid text-center">    
    <div class="row content">
      <div class="col-sm-2 sidenav">
      </div>
      <div class="col-sm-8 text-left"> 
        <h1 class="text-center">Categories</h1>
        <ul class="nav nav-tabs">
          <li class=""><a href="#list" data-toggle="tab" aria-expanded="false">List</a></li>
          <li class="active"><a href="#new" data-toggle="tab" aria-expanded="true">New</a></li>
        </ul>
        <div id="myTabContent" class="tab-content">
          <div class="tab-pane fade" id="list">
            <div class="row top10">
              <div class="col-sm-12 text-left">
                <ul class="list-inline">
                  <li v-for="(category, index) in categories" :key="index">
                    <div class="panel panel-info">
                      <div class="panel-heading">
                        <h2 class="panel-title">{{ streaming.description }}</h2>
                        <strong class="panel-title text-right">{{ (streaming.type).toUpperCase() }}</strong>
                      </div>
                      <div class="panel-body text-center">
                        <p>Image</p>
                        <hr>
                        <router-link :to="`/live/${streaming.id}`"><div class="btn btn-info">Watch now!</div></router-link>
                      </div>
                    </div>
                  </li>
                </ul>
              </div>  
            </div>
          </div>
          <div class="tab-pane fade active in" id="new">
            <div class="row top10">
              <div class="col-sm-8 text-left">
                <div class="form-group top30">
                  <p class="control-label" for="focusedInput">Focused input</p>
                  <input class="form-control" id="focusedInput" type="text" />
                </div>
              </div>  
            </div>
          </div>
        </div>
        <hr>
      </div>
      <div class="col-sm-2 sidenav">
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    props: [
      'config',
      'eventBus',
      'token'
    ],
    data () {
      return {
        categories: null
      }
    },
    created () {
      const i = this;
      const eventBus = i.eventBus;
      i.getCategories();
    },
    methods: {
      getCategories: function () {
        const i = this;
        i.$http.get(`${i.config.backendPOSTA}/generator/category`,
        {
          headers: {
            'Authorization': i.token
          }
        }
        )
        .then((result) => {
          console.log(JSON.stringify(result));
        });
      }
    }
  }
</script>

<style>
  .top10 {
    margin-top:10px;
    }
  .top15 {
    margin-top:15px;
    }
  .top30 {
    margin-top:30px;
    }
</style>
