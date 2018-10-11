import {Component, Input, NgZone, OnInit} from '@angular/core';
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  url = 'http://localhost:8080/test';

  @Input()
  public tasks: Array<Object> = [];

  constructor(private zone: NgZone, translate: TranslateService) {
    translate.setDefaultLang('en');

    // the lang to use, if the lang isn't available, it will use the current loader to get them
    translate.use('en');

    translate.setTranslation('en', {
      entityVersion: "Entity Version",
      entityId: "Entity ID",
      ehrHash: "EHR Hash",
      mTaskPlanId: "Task Plan ID",
      time: "Time",
      description: "Description",
      entityOrder: "Entity Order",
      mTaskId: "Task ID",
      lifecycleState: "Lifecycle state",
      preconditionsSatisfied: "Precondition satisfied",
      waitConditionsSatisfied: "Wait Condition satisfied",
      id: "ID",
      mWorkPlanId: "Work plan ID",
      materialisedEntityDto: "Materialized entity",
      definitionId: "definition ID",
      ownerId: "Owner ID",
      type: "Type",
      action: "Action",
      ehrId: "Ehr ID"
    });
  }

  public closeTask(task: Object) {
    const index: number = this.tasks.indexOf(task);
    this.tasks.splice(index, 1);
  }

  updateUrl(event) {
    console.log(event.value);
    this.url = event.value;
    this.ngOnInit();
  }


  ngOnInit(): void {
    let eventSource = new EventSource(this.url);
    eventSource.onmessage = (event) => {
      this.zone.run(() => {
        // Do stuff here
        let json = JSON.parse(event.data).event;
        console.info('Received data: ', json);
        this.tasks.push(json);
      });
    };
  }
}
