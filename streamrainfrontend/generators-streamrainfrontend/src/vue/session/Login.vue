<template>
  <div class="container-fluid text-center">    
    <div class="row content">
      <div class="col-sm-2 sidenav">
      </div>
      <div class="col-sm-8 text-left">
        <h1>Log In</h1>
        <div class="row">
          <div class="col-sm-4 text-left">
            <div class="form-group">
              <label class="control-label" for="focusedInput">Username</label>
              <input v-model="username" class="form-control" type="text" value="Username">
            </div>
            <div class="form-group">
              <label class="control-label">Password</label>
              <input v-model="password" class="form-control" type="password" value="Password">
            </div>
            <div class="form-group">
              <button class="btn btn-primary btn-lg">Log In</button>
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
    data () {
      return {
        username: null,
        password: null
      }
    },
  }
</script>

