import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CreateItemComponent } from './create-item/create-item.component';
import { ItemDetailsComponent } from './item-details/item-details.component';
import { ItemListComponent } from './item-list/item-list.component';
import {ReactiveFormsModule} from "@angular/forms";
import { ItemEditComponent } from './item-edit/item-edit.component';

@NgModule({
  declarations: [
    AppComponent,
    CreateItemComponent,
    ItemDetailsComponent,
    ItemListComponent,
    ItemEditComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
