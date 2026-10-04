import { Routes } from '@angular/router';
import { Template } from './template/template';
import { CadastroCartao } from './cartoes/cadastro-cartao/cadastro-cartao';
import { ListarCartoes } from './cartoes/listar-cartoes/listar-cartoes/listar-cartoes';

export const routes: Routes = [
  {
    path: '',
    component: Template,
    children: [
      {
        path: 'cadastro-cartao',
        component: CadastroCartao
      },
      {
        path: 'lista-cartoes',
        component: ListarCartoes
      }
    ]
  }
];
