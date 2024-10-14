import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

import { AppModule } from './app/app.module';

import 'codemirror/lib/codemirror';
import 'codemirror/mode/clike/clike';
import 'codemirror/mode/shell/shell'
import 'codemirror/addon/edit/matchbrackets';
import 'codemirror/addon/hint/show-hint';
import 'codemirror/addon/hint/anyword-hint';
import 'codemirror/addon/selection/mark-selection.js'

platformBrowserDynamic().bootstrapModule(AppModule)
  .catch(err => console.error(err));
