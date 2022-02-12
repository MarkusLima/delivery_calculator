<!doctype html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="pt_br" xmlns:th="http://thymeleaf.org">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <title>Calculator Delivery</title>
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.4/css/jquery.dataTables.css">
</head>
<body>
    <div class="container">
        <div class="row mt-3">
            <div class="col-md-2"></div>
            <div class="col-md-8">
                <div class="shadow p-3 bg-body rounded">

                    <c:choose>
                       <c:when test=${NOTES_SESSION != ""}>
                           <div class="alert alert-warning" role="alert">
                             <strong>${NOTES_SESSION}</strong>
                           </div>
                       </c:when>
                    </c:choose>

                    ${NOTES_SESSION = ""}

                    <form method="post" action="/save">
                        <div class="row">
                            <div class="col-md-6">
                                <label class="form-label">Nome destinatario</label>
                                <input type="text" class="form-control" placeholder="nome..." name="name_user_destination" required>
                            </div>
                            <div class="col-md-6">
                                <label class="form-label">Peso do produto</label>
                                <input type="text" class="form-control" placeholder="peso..." name="weight" required value="0.0">
                            </div>
                            <div class="col-md-6">
                                <label class="form-label">Cep Origem</label>
                                <input type="text" class="form-control" placeholder="cep..." name="zipcode_origin" required>
                            </div>
                            <div class="col-md-6">
                                <label class="form-label">Cep destino</label>
                                <input type="text" class="form-control" placeholder="cep..." name="zipcode_destination" required>
                            </div>
                        </div>
                        <div class="d-grid gap-2 col-6 mx-auto mt-3">
                            <button class="btn btn-primary" type="submit">Calcular</button>
                        </div>

                        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
                    </form>
                </div>
            </div>
            <div class="col-md-2"></div>
        </div>

        <div class="row mt-3">
            <div class="table-responsive">
                <table class="table align-middle table-hover" id="table_id">
                    <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Peso</th>
                        <th scope="col">Cep origem</th>
                        <th scope="col">Cep destino</th>
                        <th scope="col">Nome destinatatio</th>
                        <th scope="col">Valor total frete</th>
                        <th scope="col">Data prevista da entrega</th>
                        <th scope="col">Data consulta</th>
                    </tr>
                    </thead>
                    <tbody></tbody>
                </table>
            </div>
        </div>
    </div>

    <script
            src="https://code.jquery.com/jquery-3.6.0.min.js"
            integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
            crossorigin="anonymous">
    </script>

    <script
            type="text/javascript"
            src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.16/jquery.mask.min.js">
    </script>

    <script
            type="text/javascript"
            charset="utf8"
            src="https://cdn.datatables.net/1.11.4/js/jquery.dataTables.js">
    </script>

<script>
    $(document).ready( function () {

        $('#table_id').DataTable({
           ajax:{
           url:  '/api/calculators',
           type: 'GET',
           contentType: 'application/json',
           success: function(data){
                console.log(data);

                $.each(data, function (index, value) {
                    $('#table_id').dataTable().fnAddData( [
                        value.id,
                        value.weight,
                        value.zipcode_origin,
                        value.zipcode_destination,
                        value.name_user_destination,
                        'R$'+value.value_total_delivery,
                        value.date_delivery,
                        value.query_delivery,
                      ]);
                });
           },
           error: function (e) {
                console.log("There was an error with your request...");
                console.log("error: " + JSON.stringify(e));
           }
        }
        });

        $('input[name="zipcode_destination"]').mask('00000-000');
        $('input[name="zipcode_origin"]').mask('00000-000');
        $('input[name="weight"]').mask("##0.0", { reverse: true });
    });

</script>
</body>
</html>