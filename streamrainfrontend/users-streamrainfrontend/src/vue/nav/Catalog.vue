<template>
  <div class="container-fluid text-center">    
    <div class="row content">
      <div class="col-sm-2 sidenav">
      </div>
      <div class="col-sm-8 text-left">
        <div class="row content" v-if="janusAlert">
          <div class="alert alert-dismissible alert-warning">
            <button type="button" class="close" data-dismiss="alert">&times;</button>
            <p><strong>Oops!</strong></p>
            <p>{{ janusAlert.message }}</p>
          </div>
        </div>
        <h1>{{ tenant }} Catalog</h1>
        <div v-if="!catalog && !janusAlert">
          <p class="text-danger">Conectando...</p>
        </div>
        <div v-if="catalog != null">
          <table class="table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Description</th>
                <th>Type</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(streaming, index) in catalog" :key="index">
                <td>{{ streaming.id }}</td>
                <td>{{ streaming.description }}</td>
                <td><p class="text-danger">{{ streaming.type }}</p></td>
              </tr>
            </tbody>
          </table>
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
      'tenant',
      'eventBus',
      'janusAlert'
    ],
    data () {
      return {
        catalog: null
      }
    },
    created () {
      const updateCatalog = this.updateCatalog;
      this.eventBus.$emit('getJanusStreamsList', null);
      this.eventBus.$on('setJanusStreamsList', function (result) {
        updateCatalog(result);
      });
    },
    methods: {
      updateCatalog: function (catalog) {
        this.catalog = catalog;
      }
    }
  }
</script>
