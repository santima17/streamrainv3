<template>
  <div class="container-fluid text-center">    
    <div class="row content">
      <div class="col-sm-2 sidenav">
      </div>
      <div class="col-sm-8 text-left"> 
        <h1 class="text-center">Create Content</h1>
        <ul class="nav nav-tabs">
          <li class=""><a href="#home" data-toggle="tab" aria-expanded="false">Live Only</a></li>
          <li class="active"><a href="#profile" data-toggle="tab" aria-expanded="true">Always Available</a></li>
        </ul>
        <div id="myTabContent" class="tab-content">
          <div class="tab-pane fade" id="home">
          </div>
          <div class="tab-pane fade active in" id="profile">
            <div class="row">
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

<style>
  .top5 { margin-top:5px; }
  .top7 { margin-top:7px; }
  .top10 { margin-top:10px; }
  .top15 { margin-top:15px; }
  .top17 { margin-top:17px; }
  .top30 { margin-top:30px; }
</style>

