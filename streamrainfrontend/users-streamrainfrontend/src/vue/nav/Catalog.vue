<template>
  <div class="container-fluid text-center">    
    <div class="row content">
      <div class="col-sm-2 sidenav">
      </div>
      <div class="col-sm-8 text-left"> 
        <h1>{{ tenant }} Catalog</h1>
        <div v-if="catalog === null">
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
      'eventBus'
    ],
    data () {
      return {
        catalog: null
      }
    },
    created () {
      const updateCatalog = this.updateCatalog;
      const eventBus = this.eventBus;
      eventBus.$emit('getJanusStreamsList', null);
      eventBus.$on('setJanusStreamsList', function (result) {
        updateCatalog(result);
      });
    },
    methods: {
      updateCatalog: function (catalog) {
        this.catalog = catalog;
      },
    }
  }
</script>
