import { Routes } from '@angular/router';
import { Template } from './template/template';
import { CadastroCartao } from './cartoes/cadastro-cartao/cadastro-cartao';

export const routes: Routes = [
  {
    path: '',
    component: Template,
    children: [
      {
        path: 'cadastro-cartao',
        component: CadastroCartao
      }
    ]
  }
];
