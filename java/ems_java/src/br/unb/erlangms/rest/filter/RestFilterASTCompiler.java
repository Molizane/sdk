package br.unb.erlangms.rest.filter;

import br.unb.erlangms.rest.exception.RestApiException;
import br.unb.erlangms.rest.filter.ast.RestFilterAST;
import br.unb.erlangms.rest.filter.tokens.RestFilterToken;
import br.unb.erlangms.rest.provider.IRestApiProvider;
import br.unb.erlangms.rest.util.RestUtils;
import java.util.ArrayList;

/**
 * Responsável por compilar o operador filter e gerar um Abstract Syntax Tree (AST)
 *
 * @author Everton de Vargas Agilar 
 * @version 1.0.0
 * @since 26/03/2019
 */
public class RestFilterASTCompiler {

    public RestFilterAST evaluate(String expression, final IRestApiProvider apiProvider) {
        try {
            if (expression == null) {
                return null;
            }
            expression = RestUtils.unquoteString(expression).trim();
            if (expression.isEmpty() || expression.equals("{}")) {
                return null;
            }
            IRestFilterTokenizable tokenizer = new RestFilterTokenizable();
            IRestFilterParser parser = new RestFilterParser(apiProvider);
            char[] array = expression.toCharArray();
            ArrayList<RestFilterToken> tokenList = tokenizer.tokenize(array);
            RestFilterAST root = parser.parse(tokenList);
            return root;
        } catch (RestApiException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RestApiException("Operador filter inválido. Motivo: " + ex.getMessage());
        }
    }

}