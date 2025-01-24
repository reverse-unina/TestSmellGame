import { Component, Input, OnInit, VERSION, ViewChild, ElementRef } from '@angular/core';
import { Location } from '@angular/common'
import { CodemirrorComponent } from '@ctrl/ngx-codemirror';
import { CodeeditorService } from '../../services/codeeditor/codeeditor.service';

@Component({
  selector: 'app-codeeditor',
  templateUrl: './code-editor.component.html',
  styleUrls: ['./code-editor.component.css']
})

export class CodeEditorComponent implements OnInit {
  name = 'Angular ' + VERSION.major;
  @Input() injectedCode: string = "";
  @Input() editable: boolean = true;
  @ViewChild('editor') editorRef: ElementRef | any;
  @ViewChild('codemirror') codeMirror: CodemirrorComponent | any;
  resettable : boolean = false;
  isLoading : boolean = false;

  codeMirrorOptions: any = {
    mode: "text/x-java",
    indentWithTabs: true,
    smartIndent: true,
    lineNumbers: true,
    lineWrapping: false,
    extraKeys: { "Ctrl-Space": "autocomplete" },
    gutters: ["CodeMirror-linenumbers", "CodeMirror-foldgutter"],
    autoCloseBrackets: true,
    matchBrackets: true,
    lint: true,
    theme: "dracula",
    styleSelectedText: true,
  };

  isFullScreen: boolean = false;
  originalParent: HTMLElement | any;
  originalNextSibling: Node | any;
  originalInjectedCode: string = "";

  constructor(private location: Location, private codeEditorService: CodeeditorService ) { }

  ngOnInit() {
    if (!this.editable) {
      this.codeMirrorOptions['readOnly'] = 'nocursor';
    }
    this.isLoading = true;
    setTimeout(() => {
          this.originalInjectedCode = this.injectedCode;
          this.resettable = true;
          const path = this.location.path();
if (!path) {
  console.error('Percorso non disponibile.');
  return;
}
          const routeSplit = path.split('/');
          const exerciseName = routeSplit[routeSplit.length - 1];
          let keyPrefix = '';

          if (this.location.path().includes('assignments')) {
            keyPrefix = 'assignment-';
          } else if (this.location.path().includes('refactor-game')) {
            keyPrefix = 'refactoring-game-';
          }

          const savedCode = localStorage.getItem(`${keyPrefix}${exerciseName}`);
          if (savedCode) {
            const { testCode } = JSON.parse(savedCode);
            this.loadCodeFromStorage(testCode);
          }
          this.isLoading = false;
        }, 2000);
  }

  onCodeChange() {
      this.codeEditorService.setCodeModified(true);
    }

  private loadCodeFromStorage(code: string): void {
    if (this.editable)
      this.codeMirror.codeMirror.setValue(code);
  }

  resetEditor() {
    this.codeMirror.codeMirror.setValue(this.originalInjectedCode);
  }

  setEditorContent(event: Event) {
    // console.log(event, typeof event);
    // console.log(this.query);
    this.codeEditorService.setCodeModified(true);
  }

  toggleFullScreen() {
    const editor = this.editorRef.nativeElement;
    const codemirror = this.codeMirror.codeMirror;

    if (!this.isFullScreen) {
      // Entrata in modalità fullscreen
      this.originalParent = editor.parentNode as HTMLElement;
      this.originalNextSibling = editor.nextSibling;
      document.body.appendChild(editor);
      editor.style.position = 'fixed';
      editor.style.top = '0';
      editor.style.left = '0';
      editor.style.width = '100%';
      editor.style.height = '100%';
      editor.style.zIndex = '9999';
      codemirror.getWrapperElement().style.width = '100%';
      codemirror.getWrapperElement().style.height = '100%';
      codemirror.refresh();
    } else {
      // Uscita dalla modalità fullscreen
      this.originalParent.insertBefore(editor, this.originalNextSibling);
      editor.style.position = 'static';
      editor.style.width = 'auto';
      editor.style.height = '300px';
      editor.style.overflow = 'auto';
      editor.style.zIndex = 'auto';
      codemirror.getWrapperElement().style.width = 'auto';
      codemirror.getWrapperElement().style.height = 'auto';
      codemirror.refresh();
    }

    this.isFullScreen = !this.isFullScreen;
  }
}
